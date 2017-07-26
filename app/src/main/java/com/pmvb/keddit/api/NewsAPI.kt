package com.pmvb.keddit.api

import retrofit2.Call

/**
 * Created by pmvb on 17-07-26.
 */
interface NewsAPI {
    fun getNews(after: String, limit: Int): Call<RedditNewsResponse>
}