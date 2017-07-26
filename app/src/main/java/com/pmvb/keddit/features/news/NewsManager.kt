package com.pmvb.keddit.features.news

import com.pmvb.keddit.api.NewsAPI
import com.pmvb.keddit.api.NewsRestAPI
import com.pmvb.keddit.commons.RedditNewsItem
import com.pmvb.keddit.commons.RedditNewsPage
import io.reactivex.Observable

class NewsManager(private val api: NewsAPI = NewsRestAPI()) {
    fun getNews(after: String = "", limit: Int = 10): Observable<RedditNewsPage> {
        return Observable.create {
            subscriber ->
            val apiCall = api.getNews(limit = limit, after = after)
            val response = apiCall.execute()

            if (response.isSuccessful) {
                val dataResponse = response.body().data
                val news = dataResponse.children.map {
                    val item = it.data
                    RedditNewsItem(
                            author = item.author,
                            title = item.title,
                            numComments = item.num_comments,
                            created = item.created * 1000, // Convert to milliseconds
                            thumbnail = item.thumbnail,
                            url = item.url
                    )
                }
                val redditNewsPage = RedditNewsPage(
                        after = dataResponse.after ?: "",
                        before = dataResponse.before ?: "",
                        news = news
                )
                subscriber.onNext(redditNewsPage)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }

        }
    }
}