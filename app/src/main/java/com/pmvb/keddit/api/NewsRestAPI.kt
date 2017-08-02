package com.pmvb.keddit.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class NewsRestAPI @Inject constructor(private val redditApi: RedditApi): NewsAPI {

    override fun getNews(after: String, limit: Int): Call<RedditNewsResponse> {
        return redditApi.getTop(after, limit)
    }
}