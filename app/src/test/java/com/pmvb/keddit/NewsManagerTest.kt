package com.pmvb.keddit

import com.pmvb.keddit.api.*
import com.pmvb.keddit.commons.RedditNewsPage
import com.pmvb.keddit.features.news.NewsManager
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import com.nhaarman.mockito_kotlin.whenever
import com.nhaarman.mockito_kotlin.any
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Response
import java.util.*

class NewsManagerTest {
    var testSub = TestObserver<RedditNewsPage>()
    var mockAPI = mock<NewsAPI>()
    var mockCall = mock<Call<RedditNewsResponse>>()

    @Before
    fun setup() {
        testSub = TestObserver<RedditNewsPage>()
        mockAPI = mock<NewsAPI>()
        mockCall = mock<Call<RedditNewsResponse>>()
        whenever(mockAPI.getNews(any(), any())).thenReturn(mockCall)
    }

    @Test
    fun testSuccess_basic() {
        // Preparation
        val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
        val response = Response.success(redditNewsResponse)

        whenever(mockCall.execute()).thenReturn(response)

        // Call
        val newsManager = NewsManager(mockAPI)
        newsManager.getNews("").subscribe(testSub)

        // Assertion
        testSub.assertNoErrors()
        testSub.assertValueCount(1)
        testSub.assertComplete()
    }

    @Test
    fun testSuccess_checkOneNews() {
        // Preparation
        val newsData = RedditNewsDataResponse(
                "author",
                "title",
                10,
                Date().time,
                "thumbnail",
                "url"
        )
        val newsResponse = RedditChildrenResponse(newsData)
        val redditNewsResponse = RedditNewsResponse(
                RedditDataResponse(listOf(newsResponse), null, null))
        val response = Response.success(redditNewsResponse)

        whenever(mockCall.execute()).thenReturn(response)

        // Call
        val newsManager = NewsManager(mockAPI)
        newsManager.getNews("").subscribe(testSub)

        // Assertion
        testSub.assertNoErrors()
        testSub.assertValueCount(1)
        testSub.assertComplete()
        assert((testSub.events[0][0] as RedditNewsPage).news[0].author == newsData.author)
        assert((testSub.events[0][0] as RedditNewsPage).news[0].title == newsData.title)
    }

    @Test
    fun testError() {
        // Preparation
        val responseError = Response.error<RedditNewsResponse>(
                500,
                ResponseBody.create(MediaType.parse("application/json"), "")
        )
        whenever(mockCall.execute()).thenReturn(responseError)

        // Call
        val newsManager = NewsManager(mockAPI)
        newsManager.getNews("").subscribe(testSub)

        // Assertion
        assert(testSub.errorCount() == 1)
    }
}