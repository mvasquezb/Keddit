package com.pmvb.keddit.di

import android.app.Application
import android.content.Context
import com.pmvb.keddit.KedditApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by pmvb on 17-08-02.
 */
@Module
class AppModule(val app: KedditApp) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): Application = app
}