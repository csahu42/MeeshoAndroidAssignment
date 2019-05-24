package com.meesho.assignment.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.meesho.assignment.AppSharedPreferences
import com.meesho.assignment.BuildConfig
import com.meesho.assignment.commons.utils.Constants
import com.meesho.assignment.network.ApiService
import com.meesho.assignment.rx.AppSchedulerProviderProvider
import com.meesho.assignment.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * App level object provider
 */

@Module
class AppModule {

    companion object {
        private const val CLIENT_READ_TIME_OUT = 5L
        private const val CLIENT_WRITE_TIME_OUT = 5L
        private const val CLIENT_CACHE_SIZE = 10 * 1024 * 1024L // 10 MiB
        private const val CLIENT_CACHE_DIRECTORY = "http"
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application): AppSharedPreferences {
        return AppSharedPreferences(application.applicationContext)
    }

    @Provides
    @Singleton
    fun scheduler(): SchedulerProvider {
        return AppSchedulerProviderProvider()
    }


    @Provides
    @Singleton
    fun providesOkhttpCache(context: Context): Cache {
        return Cache(File(context.cacheDir, CLIENT_CACHE_DIRECTORY), CLIENT_CACHE_SIZE)
    }


    @Provides
    @Singleton
    fun providesOkHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(CLIENT_READ_TIME_OUT, TimeUnit.MINUTES)
            .writeTimeout(CLIENT_WRITE_TIME_OUT, TimeUnit.MINUTES)
            .readTimeout(CLIENT_READ_TIME_OUT, TimeUnit.MINUTES)


        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }
        return client.build()

    }


    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.AppUrl.APP_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    /*Api service*/
    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}