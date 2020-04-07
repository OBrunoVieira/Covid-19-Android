package com.doubleb.covid19.repository

import com.doubleb.covid19.model.Historical
import com.doubleb.covid19.model.HistoricalResult
import com.doubleb.covid19.model.TimeLineResult
import com.doubleb.covid19.model.BaseData
import com.doubleb.covid19.network.Covid19DataSource
import io.reactivex.rxjava3.core.Observable

class CountryRepository(private val covid19DataSource: Covid19DataSource) {
    fun getCountry(country: String): Observable<ArrayList<BaseData>> = run {
        val baseList = arrayListOf<BaseData>()
        covid19DataSource.getByCountry(country)
            .flatMap {
                baseList.add(BaseData(country = it))
                getHistoricalByCountry(country)
            }.map {
                baseList.apply {
                    add(BaseData(historical = it))
                }
            }
    }

    private fun getHistoricalByCountry(country: String): Observable<HistoricalResult> =
        covid19DataSource.getHistoricalByCountry(country)
            .onErrorResumeNext { Observable.just(Historical()) }
            .map {
                val casesList: List<TimeLineResult>
                val deathList: List<TimeLineResult>
                val recoveredList: List<TimeLineResult>

                it.apply {
                    casesList = it.timeline.casesMap.map { entry ->
                        TimeLineResult(entry.key, entry.value)
                    }.takeLast(7)

                    deathList = it.timeline.deathsMap.map { entry ->
                        TimeLineResult(entry.key, entry.value)
                    }.takeLast(7)

                    recoveredList = it.timeline.recoveredMap.map { entry ->
                        TimeLineResult(entry.key, entry.value)
                    }.takeLast(7)
                }


                HistoricalResult(
                    it.country,
                    casesList,
                    deathList,
                    recoveredList
                )
            }
}