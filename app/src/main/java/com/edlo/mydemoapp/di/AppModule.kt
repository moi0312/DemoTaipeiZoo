package com.edlo.mydemoapp.di

import android.content.Context
import com.edlo.mydemoapp.MyDemoApp
import com.edlo.mydemoapp.repository.local.TaipeiZooDB
import com.edlo.mydemoapp.repository.local.TpzDbHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return MyDemoApp.INSTANCE.applicationContext
    }

    @Provides
    @Singleton
    fun provideTaipeiZooDB(context: Context): TaipeiZooDB {
        return TaipeiZooDB.getDatabase(context)
    }

}