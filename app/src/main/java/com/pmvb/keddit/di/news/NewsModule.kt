package com.pmvb.keddit.di.news

import com.pmvb.keddit.api.NewsAPI
import com.pmvb.keddit.api.NewsRestAPI
import com.pmvb.keddit.api.RedditApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by pmvb on 17-08-02.
 */
@Module
class NewsModule {
    @Provides
    @Singleton
    fun provideNewsAPI(redditApi: RedditApi): NewsAPI = NewsRestAPI(redditApi)

    @Provides
    @Singleton
    fun  provideRedditAPI(retrofit: Retrofit): RedditApi = retrofit.create(RedditApi::class.java)

    /**
     * NewsManager is automatically provided by Dagger as we set the @Inject annotation in the
     * constructor, so we can avoid adding a 'provider method' here.
     */
}