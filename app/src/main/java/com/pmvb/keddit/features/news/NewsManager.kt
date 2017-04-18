package com.pmvb.keddit.features.news

import android.util.Log
import com.pmvb.keddit.api.RestAPI
import com.pmvb.keddit.commons.RedditNewsItem
import io.reactivex.Observable

class NewsManager(private val api: RestAPI = RestAPI()) {
    fun getNews(limit: Int = 10): Observable<List<RedditNewsItem>> {
        return Observable.create {
            subscriber ->
            val apiCall = api.getNews(limit = limit, after = "")
            val response = apiCall.execute()

            if (response.isSuccessful) {
                val news = response.body().data.children.map {
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
                subscriber.onNext(news)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }

        }
    }
}