package com.johancarinus.dogbox.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.selects.select

/*
* https://github.com/nickbutcher/plaid/blob/main/core/src/main/java/io/plaidapp/core/ui/recyclerview/InfiniteScrollListener.kt
* */
abstract class InfiniteScrollListener (private val layoutManager: StaggeredGridLayoutManager) : RecyclerView.OnScrollListener() {

    private val loadMoreRunnable = Runnable { onLoadMore() }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        // bail out if scrolling upward or already loading data
        if (dy < 0 || isDataLoading()) return

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = layoutManager.itemCount
        val positionsArray = IntArray(2)
        layoutManager.findFirstVisibleItemPositions(positionsArray)
        val firstVisibleItem = positionsArray.maxOrNull()

        if (totalItemCount - visibleItemCount <= firstVisibleItem?.plus(VISIBLE_THRESHOLD) ?: 0) {
            recyclerView.post(loadMoreRunnable)
        }
    }

    abstract fun onLoadMore()

    abstract fun isDataLoading(): Boolean

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
}