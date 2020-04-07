package com.doubleb.covid19.repository

import com.doubleb.covid19.model.*
import com.doubleb.covid19.network.Covid19DataSource
import io.reactivex.rxjava3.core.Observable

class WorldRepository(private val covid19DataSource: Covid19DataSource) {

    fun getWorldCases(): Observable<List<BaseData>> = run {
        val worldList = arrayListOf<BaseData>()
        covid19DataSource.getWorldCases()
            .flatMap {
                worldList.add(BaseData(country = it))
                getWorldHistorical()
            }
            .map {
                worldList.apply {
                    add(BaseData(historical = it.historical))
                }
            }
    }


    fun getCasesByCountries(): Observable<List<BaseData>> =
        covid19DataSource.getCasesByCountries()
            .flatMapIterable { it }
            .map { BaseData(it) }
            .toSortedList { o1, o2 ->
                val countryOneCases = o1.country?.cases ?: 0
                val countryTwoCases = o2.country?.cases ?: 0
                countryTwoCases.compareTo(countryOneCases)
            }
            .toObservable()

    private fun getWorldHistorical(): Observable<BaseData> =
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


                BaseData(
                    historical = HistoricalResult(
                        cases = casesList,
                        deaths = deathList,
                        recovered = recoveredList
                    )
                )
            }
}