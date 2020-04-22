package com.doubleb.covid19.network

import com.doubleb.covid19.model.NewsResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleNewsDataSource {

    @GET("top-headlines?category=health&q=covid")
    fun getTopHeadlines(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10,
        @Query("country") country: String
    ): Observable<NewsResult>
}