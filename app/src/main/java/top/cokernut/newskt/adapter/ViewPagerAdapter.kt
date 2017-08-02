package top.cokernut.newskt.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import top.cokernut.newskt.R
import top.cokernut.newskt.fragment.NewListFragment
import top.cokernut.newskt.model.URLModel

import kotlinx.android.synthetic.main.item_default_tab.view.*

class ViewPagerAdapter(private val mContext: Context, fm: FragmentManager, private val mDatas: List<URLModel>, private val mFragments: List<NewListFragment>) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getItem(position: Int): NewListFragment {
        return mFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // return mDatas.get(position).getTitle();
        return null
    }

    fun getTabView(position: Int): View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_tab, null)
        view.tv_title.text = mDatas[position].title
        return view
    }

    fun getDefaultTabView(position: Int): View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_default_tab, null) as TextView
        view.text = mDatas[position].title
        return view
    }

}