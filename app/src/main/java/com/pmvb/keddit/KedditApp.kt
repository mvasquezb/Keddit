package com.pmvb.keddit

import android.app.Application
import com.pmvb.keddit.di.AppModule
import com.pmvb.keddit.di.news.DaggerNewsComponent
import com.pmvb.keddit.di.news.NewsComponent
import com.pmvb.keddit.di.news.NewsModule

/**
 * Created by pmvb on 17-08-02.
 */
class KedditApp: Application() {
    companion object {
        lateinit var newsComponent: NewsComponent
    }

    override fun onCreate() {
        super.onCreate()
        newsComponent = DaggerNewsComponent.builder()
                .appModule(AppModule(this))
//                .newsModule(NewsModule()) // Module with empty constructor implicitly created by dagger
                .build()
    }
}