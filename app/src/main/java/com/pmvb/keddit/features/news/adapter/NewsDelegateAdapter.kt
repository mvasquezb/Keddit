package com.pmvb.keddit.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.pmvb.keddit.R
import com.pmvb.keddit.commons.RedditNewsItem
import com.pmvb.keddit.commons.adapter.ViewType
import com.pmvb.keddit.commons.adapter.ViewTypeDelegateAdapter
import com.pmvb.keddit.commons.extensions.friendlyTime
import com.pmvb.keddit.commons.extensions.inflate
import com.pmvb.keddit.commons.extensions.loadImage
import kotlinx.android.synthetic.main.news_item.view.*

class NewsDelegateAdapter(val listener: onViewSelectedListener): ViewTypeDelegateAdapter {

    interface onViewSelectedListener {
        fun onItemSelected(url: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return NewsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as NewsViewHolder).bind(item as RedditNewsItem)
    }

    inner class NewsViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
            parent.inflate(R.layout.news_item)
    ) {
        fun bind(item: RedditNewsItem) = with (itemView) {
            img_thumbnail.loadImage(item.thumbnail)
            description.text = item.title
            author.text = item.author
            comments.text = resources.getString(R.string.comment_count, item.numComments)
            time.text = item.created.friendlyTime()
            url.text = item.url
            super.itemView.setOnClickListener(clickListener)
        }

        private val clickListener = { view: View ->
            listener.onItemSelected(view.url.text as String)
        }
    }
}