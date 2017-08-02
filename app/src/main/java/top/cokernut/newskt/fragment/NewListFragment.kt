package top.cokernut.newskt.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.fastjson.JSON
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_new_list.*
import top.cokernut.newskt.R
import top.cokernut.newskt.activity.DetailActivity
import top.cokernut.newskt.adapter.NewsAdapter
import top.cokernut.newskt.base.OnRVScrollListener
import top.cokernut.newskt.config.URLConfig
import top.cokernut.newskt.model.NewModel
import top.cokernut.newskt.net.HttpCall

class NewListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var urlStr:String? = null
    private var mAdapter: NewsAdapter? = null
    private var mDatas = ArrayList<NewModel>()
    private val pageSize = 10
    private var pageIndex = 1
    private var isLoading = false
    private var isTop = true

    companion object {
        val URL_STR = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            urlStr = arguments.getString(URL_STR)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_new_list, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        initView()
        onRefresh()
    }

    private fun initView() {
        srl.setColorSchemeResources(R.color.wheat, R.color.accent)
        srl.setOnRefreshListener(this)
        rv.layoutManager = LinearLayoutManager(this.activity)
        rv.itemAnimator = DefaultItemAnimator()
        mAdapter = NewsAdapter(activity, mDatas)
        rv.adapter = mAdapter

        rv.addOnScrollListener(object : OnRVScrollListener() {
            override fun onBottom() {
                getData()
            }

            override fun onTop() {
                isTop = true
            }
        })
        mAdapter!!.setOnItemClickLitener(object : NewsAdapter.OnItemClickLitener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(activity, DetailActivity().javaClass)
                intent.putExtra(DetailActivity.URL_STR, mDatas[position].url)
                intent.putExtra(DetailActivity.URL_IMG, mDatas[position].picUrl)
                startActivity(intent)
            }
        })
    }

    fun goTop() {
        if (rv.layoutManager is LinearLayoutManager) {
            (rv.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
            isTop = true
        }
    }

    override fun onRefresh() {
        pageIndex = 1
        srl.isRefreshing = true
        getData()
    }

    fun getData() {
        if (!isLoading) {
           /* var i = 1
            do {
                val model = NewModel("2017-08-02 07:09", "小米6C配新款澎湃X1处理器  隐藏式双摄1999元起售", "腾讯科技", "http://inews.gtimg.com/newsapp_ls/0/1868807009_300240/0", "http://digi.tech.qq.com/a/20170802/003841.htm")
                mDatas.add(model)
                i++
            } while (i > 10)
            mAdapter?.setDatas(mDatas)
            srl.setRefreshing(false)
            isLoading = false*/
            val params = HashMap<String, Any>()
            params.put("key", URLConfig.API_KEY)
            params.put("num", pageSize)
            params.put("page", pageIndex)
            //RxJava
            /**
            Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
            Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
            Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
            Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
            另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。*/
            HttpCall.getApiService().getNewsRx(urlStr, params)
                    //subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程,或者叫做事件产生的线程。
                    //subscribeOn() 的位置放在哪里都可以，但它是只能调用一次的。当使用了多个 subscribeOn() 的时候，只有第一个 subscribeOn() 起作用。
                    //虽然超过一个的 subscribeOn() 对事件处理的流程没有影响，但在流程之前却是可以利用的,它和 Subscriber.onStart() 同样是在 subscribe() 调用后而且在事件发送前执行，
                    // 但区别在于它可以指定线程。默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；
                    // 而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程
                    // 在 doOnSubscribe()的后面跟一个 subscribeOn() ，就能指定准备工作的线程了
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(object : Consumer<Disposable> {
                        @Throws(Exception::class)
                        override fun accept(disposable: Disposable) {
                            isLoading = true
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    //observeOn(): 指定 Subscriber 所运行在的线程,或者叫做事件消费的线程。observeOn() 指定的是它之后的操作所在的线程。
                    //因此如果有多次切换线程的需求，只要在每个想要切换线程的位置调用一次 observeOn() 即可
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<String> {
                        private var disposable: Disposable? = null
                        //解除订阅
                        override fun onSubscribe(d: Disposable) {
                            disposable = d
                        }

                        override fun onNext(s: String) {
                            if (TextUtils.isEmpty(s)) {
                                disposable!!.dispose()//当s为空时解除订阅
                            }
                            val json = JSON.parseObject(s)
                            val result = JSON.parseArray(json.getString("newslist"), NewModel::class.java)
                            if (result != null && result.size > 0) {
                                mDatas.addAll(result)
                                pageIndex++
                                mAdapter?.setDatas(mDatas)
                            } else {
                              Snackbar.make(rv, "没有更多了！", Snackbar.LENGTH_SHORT).show()
                            }
                            srl.setRefreshing(false)
                        }

                        override fun onError(e: Throwable) {
                            isLoading = false
                        }

                        override fun onComplete() {
                            isLoading = false
                        }
                    })

        }
    }
}
