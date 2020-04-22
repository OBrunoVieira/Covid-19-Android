package com.doubleb.covid19.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.doubleb.covid19.model.News
import com.doubleb.covid19.model.NewsResult
import com.doubleb.covid19.repository.NewsRepository
import com.doubleb.covid19.repository.data_source.NewsDataSource

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val liveData: LiveData<PagedList<News>>
    val liveDataState = Transformations.switchMap<NewsDataSource, DataSource<NewsResult>>(
        newsRepository.liveDataNews,
        NewsDataSource::liveDataState
    )

    init {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        liveData = LivePagedListBuilder<Int, News>(newsRepository, config).build()
    }

    fun retry(){
        newsRepository.retry()
    }

    fun clearViewModel() {
        newsRepository.clearDisposables()
    }

    override fun onCleared() {
        super.onCleared()
        clearViewModel()
    }
}