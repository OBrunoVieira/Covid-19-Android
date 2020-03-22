package com.doubleb.covid19.network

import com.doubleb.covid19.model.Country
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface Covid19DataSource{

    @GET("/countries/{country}")
    fun getByCountry(@Path("country") country : String) : Observable<Country>

    @GET("/all")
    fun getWorldCases() : Observable<Country>

    @GET("/countries")
    fun getCasesByCountries() : Observable<List<Country>>
}