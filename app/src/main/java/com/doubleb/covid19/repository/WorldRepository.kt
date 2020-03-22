package com.doubleb.covid19.repository

import com.doubleb.covid19.model.WorldData
import com.doubleb.covid19.network.Covid19DataSource
import io.reactivex.rxjava3.core.Observable

class WorldRepository(private val covid19DataSource: Covid19DataSource) {

    fun getWorldCases(): Observable<WorldData> =
        covid19DataSource.getWorldCases().map { WorldData(it) }

    fun getCasesByCountries(): Observable<List<WorldData>> =
        covid19DataSource.getCasesByCountries()
            .flatMap { Observable.fromIterable(it) }
            .map { WorldData(it) }
            .toList()
            .toObservable()
}