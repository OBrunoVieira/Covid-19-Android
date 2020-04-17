package com.doubleb.covid19.repository

import com.doubleb.covid19.model.*
import com.doubleb.covid19.network.Covid19DataSource
import io.reactivex.rxjava3.core.Observable

class WorldRepository(private val covid19DataSource: Covid19DataSource) {

    fun getWorldCases(): Observable<WorldData> = run {
        val worldData = WorldData()
        covid19DataSource.getWorldCases()
            .flatMap {
                worldData.country = it
                return@flatMap getWorldHistorical()
            }
            .map {
                worldData.historical = it
                return@map worldData
            }
    }


    fun getCasesByCountries(): Observable<List<Country>> =
        covid19DataSource.getCasesByCountries()
            .flatMapIterable { it }
            .toSortedList { o1, o2 ->
                val countryOneCases = o1.cases
                val countryTwoCases = o2.cases
                countryTwoCases.compareTo(countryOneCases)
            }
            .toObservable()

    private fun getWorldHistorical(): Observable<HistoricalResult> =
        covid19DataSource.getWorldHistorical()
            .onErrorResumeNext { Observable.just(TimeLine()) }
            .map {
                val casesList: List<TimeLineResult>
                val deathList: List<TimeLineResult>
                val recoveredList: List<TimeLineResult>

                it.apply {
                    casesList = it.casesMap.map { entry ->
                        TimeLineResult(entry.key, entry.value)
                    }.takeLast(7)

                    deathList = it.deathsMap.map { entry ->
                        TimeLineResult(entry.key, entry.value)
                    }.takeLast(7)

                    recoveredList = it.recoveredMap.map { entry ->
                        TimeLineResult(entry.key, entry.value)
                    }.takeLast(7)
                }

                HistoricalResult(cases = casesList, deaths = deathList, recovered = recoveredList)
            }
}