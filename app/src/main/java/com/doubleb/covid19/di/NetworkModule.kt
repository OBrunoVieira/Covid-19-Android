package com.doubleb.covid19.di

import android.content.Context
import com.doubleb.covid19.BuildConfig.*
import com.doubleb.covid19.extensions.isNetworkConnected
import com.doubleb.covid19.network.Covid19DataSource
import com.doubleb.covid19.network.GoogleNewsDataSource
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    factory { createCovidApi(androidContext()) }

    factory(named("GoogleNewsApi")) { createGoogleNewsApi(androidContext()) }
}

private fun createCovidApi(context: Context): Covid19DataSource = run {
    val retrofit = Retrofit.Builder()
        .baseUrl(HOST_CORONA)
        .client(getCovidHttpClient(context))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    retrofit.create(Covid19DataSource::class.java)
}

private fun createGoogleNewsApi(context: Context) = run {
    val retrofit = Retrofit.Builder()
        .baseUrl(HOST_GOOGLE_NEWS)
        .client(getGoogleNewsHttpClient(context))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    retrofit.create(GoogleNewsDataSource::class.java)
}

private fun getGoogleNewsHttpClient(context: Context) =
    buildOkHttpClient(context)
        .addNetworkInterceptor(createGoogleNewsCachePolicy(context))
        .build()

private fun getCovidHttpClient(context: Context) =
    buildOkHttpClient(context)
        .addNetworkInterceptor(createCoronaCachePolicy(context))
        .build()

private fun buildOkHttpClient(context: Context) = run {
    val loggingInterceptor =
        HttpLoggingInterceptor().apply { level = if (DEBUG) Level.BODY else Level.NONE }

    OkHttpClient.Builder()
        .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
}

fun createCoronaCachePolicy(context: Context) = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain) = run {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        if (request.method == "GET") {
            val cacheControl = if (context.isNetworkConnected()) {
                CacheControl.Builder().onlyIfCached().maxAge(2, TimeUnit.MINUTES).build()
            } else {
                CacheControl.Builder().maxStale(1, TimeUnit.DAYS).build()
            }

            requestBuilder.cacheControl(cacheControl)
        }

        return@run chain.proceed(requestBuilder.build())
    }
}

private fun createGoogleNewsCachePolicy(context: Context) = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain) = run {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
            .addHeader("x-api-key", GOOGLE_NEWS_API_KEY)

        if (request.method == "GET") {
            val cacheControl = if (context.isNetworkConnected()) {
                CacheControl.Builder().maxAge(2, TimeUnit.HOURS).build()
            } else {
                CacheControl.Builder().maxStale(1, TimeUnit.DAYS).build()
            }

            requestBuilder.cacheControl(cacheControl)
        }

        return@run chain.proceed(requestBuilder.build())
    }
}
