package com.doubleb.covid19.network

import com.doubleb.covid19.model.Result
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface Covid19DataSource{

    @GET("/countries/{country}")
    fun getByCountry(@Path("country") country : String) : Observable<Result>
}