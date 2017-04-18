package com.pmvb.keddit.features.news

import com.pmvb.keddit.commons.RedditNewsItem
import io.reactivex.Observable
import java.util.Date

class NewsManager {
    fun getNews(): Observable<List<RedditNewsItem>> {
        return Observable.create {
            subscriber ->

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
            subscriber.onNext(news)
        }
    }
}