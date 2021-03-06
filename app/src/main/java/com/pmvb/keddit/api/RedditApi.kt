package com.pmvb.keddit.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("/top.json")
    fun getTop(@Query("after") after: String = "",
               @Query("limit") limit: Int = 10): Call<RedditNewsResponse>
}