package com.doubleb.covid19.di

import com.doubleb.covid19.BuildConfig.DEBUG
import com.doubleb.covid19.BuildConfig.SERVER_URL
import com.doubleb.covid19.network.Covid19DataSource
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { createHttpClient(get()) }
    factory { createOkHttpClient() }
}

private fun createHttpClient(okHttpClient: OkHttpClient): Covid19DataSource = run {
    val retrofit = Retrofit.Builder()
        .baseUrl(SERVER_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    retrofit.create(Covid19DataSource::class.java)
}

private fun createOkHttpClient() = run {
    val loggingInterceptor =
        HttpLoggingInterceptor().apply { level = if (DEBUG) Level.BODY else Level.NONE }

    OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()
}
