package com.pmvb.keddit.di.news

import com.pmvb.keddit.di.AppModule
import com.pmvb.keddit.di.NetworkModule
import com.pmvb.keddit.features.news.NewsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by pmvb on 17-08-02.
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NewsModule::class,
        NetworkModule::class
))
interface NewsComponent {
    fun inject(newsFragment: NewsFragment)
}