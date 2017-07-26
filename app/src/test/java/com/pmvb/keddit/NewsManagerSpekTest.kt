package com.pmvb.keddit

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.pmvb.keddit.api.*
import com.pmvb.keddit.commons.RedditNewsPage
import com.pmvb.keddit.features.news.NewsManager
import io.reactivex.observers.TestObserver
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Response
import java.util.*

@RunWith(JUnitPlatform::class)
class NewsManagerSpekTest : Spek ({
    given("a NewsManager") {
        var testSub = TestObserver<RedditNewsPage>()
        var mockAPI = mock<NewsAPI>()
        var mockCall = mock<Call<RedditNewsResponse>>()

        beforeEachTest {
            testSub = TestObserver<RedditNewsPage>()
            mockAPI = mock<NewsAPI>()
            mockCall = mock<Call<RedditNewsResponse>>()

            whenever(mockAPI.getNews(any(), any())).thenReturn(mockCall)
        }

        context("service returns something") {
            beforeEachTest {
                // Preparation
                val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
                val response = Response.success(redditNewsResponse)

                whenever(mockCall.execute()).thenReturn(response)

                // Call
                val newsManager = NewsManager(mockAPI)
                newsManager.getNews("").subscribe(testSub)
            }

            it("should receive something and no errors") {
                testSub.assertNoErrors()
                testSub.assertValueCount(1)
                testSub.assertComplete()
            }
        }

        context("service returns just one news") {
            val newsData = RedditNewsDataResponse(
                    "author",
                    "title",
                    10,
                    Date().time,
                    "thumbnail",
                    "url"
            )
            beforeEachTest {
                // Preparation
                val newsResponse = RedditChildrenResponse(newsData)
                val redditNewsResponse = RedditNewsResponse(
                        RedditDataResponse(listOf(newsResponse), null, null))
                val response = Response.success(redditNewsResponse)

                whenever(mockCall.execute()).thenReturn(response)

                // Call
                val newsManager = NewsManager(mockAPI)
                newsManager.getNews("").subscribe(testSub)
            }

            it("should process only one news successfully") {
                // Assertion
                testSub.assertNoErrors()
                testSub.assertValueCount(1)
                testSub.assertComplete()
                assert((testSub.events[0][0] as RedditNewsPage).news[0].author == newsData.author)
                assert((testSub.events[0][0] as RedditNewsPage).news[0].title == newsData.title)
            }
        }

        context("service returns a 500 error") {
            beforeEachTest {
                // Preparation
                val responseError = Response.error<RedditNewsResponse>(
                        500,
                        ResponseBody.create(MediaType.parse("application/json"), "")
                )
                whenever(mockCall.execute()).thenReturn(responseError)

                // Call
                val newsManager = NewsManager(mockAPI)
                newsManager.getNews("").subscribe(testSub)
            }

            it("should receive an onError message") {
                assert(testSub.errorCount() == 1)
            }
        }
    }

})