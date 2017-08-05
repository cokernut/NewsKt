package top.cokernut.newskt.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class OnRVScrollListener(
        /**
         * 最后一个可见的item的位置
         */
        private var lastVisibleItemPosition: Int = 0,
        /**
         * 第一个可见的item的位置
         */
        private var firstVisibleItemPosition: Int = 0) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView!!.layoutManager
        firstVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val visibleItemCount = layoutManager.getChildCount()
        val totalItemCount = layoutManager.getItemCount()
        if (firstVisibleItemPosition == 0) {
            onTop()
        } else if (visibleItemCount > 0 && lastVisibleItemPosition + 1 >= totalItemCount) {
            onBottom()
        } else {
            onCenter()
        }

    }

    abstract fun onBottom()

    abstract fun onTop()

    fun onCenter() {}
}