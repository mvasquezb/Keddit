package com.pmvb.keddit.features.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmvb.keddit.R
import com.pmvb.keddit.commons.inflate
import kotlinx.android.synthetic.main.news_fragment.news_list

class NewsFragment : Fragment() {
    private val newsList by lazy {
        news_list
    }

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.news_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        newsList.setHasFixedSize(true)
        newsList.layoutManager = LinearLayoutManager(context)
    }
}