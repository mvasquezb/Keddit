package com.pmvb.keddit

import com.pmvb.keddit.api.NewsAPI
import com.pmvb.keddit.api.RedditDataResponse
import com.pmvb.keddit.api.RedditNewsResponse
import com.pmvb.keddit.commons.RedditNewsPage
import com.pmvb.keddit.features.news.NewsManager
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Response

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)

class NewsManagerTest {
    var testSub = TestObserver<RedditNewsPage>()
    var mockAPI = mock<NewsAPI>()
    var mockCall = mock<Call<RedditNewsResponse>>()

    @Before
    fun setup() {
        testSub = TestObserver<RedditNewsPage>()
        mockAPI = mock<NewsAPI>()
        mockCall = mock<Call<RedditNewsResponse>>()
        `when`(mockAPI.getNews(Matchers.anyString(), Matchers.anyInt())).thenReturn(mockCall)
    }

    @Test
    fun testSuccess_basic() {
        // Preparation
        val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
        val response = Response.success(redditNewsResponse)

        `when`(mockCall.execute()).thenReturn(response)

        // Call
        val newsManager = NewsManager(mockAPI)
        newsManager.getNews("").subscribe(testSub)

        // Assertion
        testSub.assertNoErrors()
        testSub.assertValueCount(1)
        testSub.assertComplete()
    }
}