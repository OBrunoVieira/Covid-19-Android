package com.doubleb.covid19.repository

import com.doubleb.covid19.network.Covid19DataSource

class HomeRepository(private val covid19DataSource: Covid19DataSource) {
    fun getCountry(country : String) =
        covid19DataSource.getByCountry(country)
}