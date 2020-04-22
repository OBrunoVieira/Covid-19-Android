package com.doubleb.covid19.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.doubleb.covid19.model.News
import com.doubleb.covid19.repository.data_source.NewsDataSource

class NewsRepository(private val newsDataSource: NewsDataSource) :
    DataSource.Factory<Int, News>() {

    val liveDataNews = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, News> {
        liveDataNews.postValue(newsDataSource)
        return newsDataSource
    }

    fun retry() {
        newsDataSource.retry()
    }

    fun clearDisposables() {
        newsDataSource.clearDisposables()
    }
}