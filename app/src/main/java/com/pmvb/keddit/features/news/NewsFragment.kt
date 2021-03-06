package com.pmvb.keddit.features.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pmvb.keddit.KedditApp
import com.pmvb.keddit.R
import com.pmvb.keddit.commons.InfiniteScrollListener
import com.pmvb.keddit.commons.RedditNewsItem
import com.pmvb.keddit.commons.RedditNewsPage
import com.pmvb.keddit.commons.RxBaseFragment
import com.pmvb.keddit.commons.extensions.inflate
import com.pmvb.keddit.features.news.adapter.NewsAdapter
import com.pmvb.keddit.features.news.adapter.NewsDelegateAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_fragment.news_list
import javax.inject.Inject

class NewsFragment : RxBaseFragment(), NewsDelegateAdapter.onViewSelectedListener {
    override fun onItemSelected(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    companion object {
        private val NEWS_PAGE_KEY = "redditNewsPage"
    }

    private var redditNewsPage: RedditNewsPage? = null
    @Inject lateinit var newsManager: NewsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KedditApp.newsComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.news_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        news_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))
        }
        initAdapter()
        if (savedInstanceState == null) {
            requestNews()
        } else if (savedInstanceState.containsKey(NEWS_PAGE_KEY)) {
            redditNewsPage = savedInstanceState.get(NEWS_PAGE_KEY) as RedditNewsPage
            (news_list.adapter as NewsAdapter).setNews(redditNewsPage!!.news)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = (news_list.adapter as NewsAdapter).getNews()
        if (redditNewsPage != null && news.size > 0) {
            outState.putParcelable(NEWS_PAGE_KEY, redditNewsPage?.copy(news = news))
        }
    }

    private fun requestNews() {
        val subscription = newsManager.getNews(
                after = redditNewsPage?.after ?: ""
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            retrievedNews ->
                            redditNewsPage = retrievedNews
                            (news_list.adapter as NewsAdapter).addNews(retrievedNews.news)
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
            news_list.adapter = NewsAdapter(this)
        }
    }
}