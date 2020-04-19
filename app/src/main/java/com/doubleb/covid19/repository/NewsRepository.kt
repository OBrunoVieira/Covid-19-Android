package com.doubleb.covid19.repository

import com.doubleb.covid19.network.GoogleNewsDataSource

class NewsRepository(private val googleNewsDataSource: GoogleNewsDataSource) {

    fun getTopHeadlines() = googleNewsDataSource.getTopHeadlines()
}