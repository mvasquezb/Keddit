package com.pmvb.keddit.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.pmvb.keddit.commons.adapter.ViewTypeDelegateAdapter
import com.pmvb.keddit.commons.adapter.ViewType
import com.pmvb.keddit.R
import com.pmvb.keddit.commons.extensions.inflate

class LoadingDelegateAdapter: ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class LoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
            parent.inflate(R.layout.news_item_loading)
    )
}