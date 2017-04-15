package com.pmvb.keddit.features.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmvb.keddit.R
import com.pmvb.keddit.commons.RedditNewsItem
import com.pmvb.keddit.commons.extensions.inflate
import com.pmvb.keddit.features.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.news_fragment.news_list
import java.util.*

class NewsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.news_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        news_list.setHasFixedSize(true)
        news_list.layoutManager = LinearLayoutManager(context)

        initAdapter()
        if (savedInstanceState == null) {
            val news = mutableListOf<RedditNewsItem>()
            (1..10).map {
                news.add(RedditNewsItem(
                        author = "author$it",
                        title = "Title $it",
                        numComments = it,
                        created = Date().time - it * 1000 * 3600,
                        thumbnail = "http://lorempixel.com/200/200/technics/$it",
                        url = "url"
                ))
            }
            (news_list.adapter as NewsAdapter).addNews(news)
        }
    }

    private fun initAdapter() {
        if (news_list.adapter == null) {
            news_list.adapter = NewsAdapter()
        }
    }
}