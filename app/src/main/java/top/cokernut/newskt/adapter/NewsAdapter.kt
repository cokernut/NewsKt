package top.cokernut.newskt.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import top.cokernut.newskt.R
import top.cokernut.newskt.model.NewModel
import java.util.ArrayList

/**
 * Created by Administrator on 2017/7/31.
 */
class NewsAdapter(private val mContext: Activity, datas: ArrayList<NewModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private var mInflater: LayoutInflater
    private var mDatas = ArrayList<NewModel>()

    private var mOnItemClickLitener: OnItemClickLitener? = null

    init {
        this.mDatas = datas
        this.mInflater = LayoutInflater.from(mContext)
    }

    override fun onClick(view: View) {
        if (mOnItemClickLitener != null) {
            mOnItemClickLitener!!.onItemClick(view, view.tag as Int)
        }
    }

    fun setDatas(datas: ArrayList<NewModel>) {
        mDatas = datas
        notifyDataSetChanged()
    }

    interface OnItemClickLitener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickLitener(mOnItemClickLitener: OnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(mInflater.inflate(R.layout.item_new_list, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.title.text = mDatas[position].title
            holder.time.text = mDatas[position].ctime
            if (!TextUtils.isEmpty(mDatas[position].picUrl)) {
                holder.img.visibility = View.VISIBLE
                val options = RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                Glide.with(mContext)
                        .load(mDatas[position].picUrl)
                        .apply(options)
                        .into(holder.img)
            } else {
                holder.img.visibility = View.GONE
            }
            mOnItemClickLitener?.let{
                holder.itemView.setOnClickListener(this)
                holder.itemView.setTag(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }


    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var time: TextView
        var img: ImageView

        init {
            title = itemView.findViewById<View>(R.id.title) as TextView
            time = itemView.findViewById<View>(R.id.time) as TextView
            img = itemView.findViewById<View>(R.id.img) as ImageView
        }
    }
}
