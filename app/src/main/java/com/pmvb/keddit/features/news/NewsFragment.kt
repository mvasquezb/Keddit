package com.pmvb.keddit.features.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmvb.keddit.R
import com.pmvb.keddit.commons.RxBaseFragment
import com.pmvb.keddit.commons.extensions.inflate
import com.pmvb.keddit.features.news.adapter.NewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.news_fragment.news_list

class NewsFragment : RxBaseFragment() {

    private val newsManager by lazy {
        NewsManager()
    }

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
            requestNews()
        }
    }

    private fun requestNews() {
        val subscription = newsManager.getNews()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            retrievedNews ->
                            (news_list.adapter as NewsAdapter).addNews(retrievedNews)
                        },
                        {
                            e ->
                            Snackbar.make(news_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                        }
                )
        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if (news_list.adapter == null) {
            news_list.adapter = NewsAdapter()
        }
    }
}