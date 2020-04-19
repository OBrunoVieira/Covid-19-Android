package com.doubleb.covid19.network

import com.doubleb.covid19.model.NewsResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface GoogleNewsDataSource {

    @GET("top-headlines?country=br&category=health&q=corona&page=1&pageSize=10")
    fun getTopHeadlines(): Observable<NewsResult>
}