package com.doubleb.covid19.repository

import com.doubleb.covid19.network.Covid19DataSource

class SearchRepository(private val dataSource: Covid19DataSource) {
    fun getCountryNames() = dataSource.getCasesByCountries()
}