package com.pmvb.keddit.features.news.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.pmvb.keddit.commons.RedditNewsItem
import com.pmvb.keddit.commons.adapter.*
import kotlin.collections.ArrayList

class NewsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object: ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.NEWS, NewsDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return this.items[position].getViewType()
    }

    fun addNews(news: List<RedditNewsItem>) {
        val initPosition = items.size - 1
        items.addAll(initPosition, news)
        notifyItemRangeInserted(initPosition, news.size)
    }

    fun setNews(news: List<RedditNewsItem>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    fun getNews(): List<RedditNewsItem> {
        return items
                .filter { it.getViewType() == AdapterConstants.NEWS }
                .map { it as RedditNewsItem }
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}
