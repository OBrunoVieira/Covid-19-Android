package com.doubleb.covid19.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleb.covid19.model.CountryData
import com.doubleb.covid19.model.NewsResult
import com.doubleb.covid19.repository.NewsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val liveData = MutableLiveData<DataSource<NewsResult>>()
    private var newsResult: NewsResult? = null

    fun getTopHeadlines() {
        compositeDisposable.add(
            newsRepository.getTopHeadlines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveData.postValue(DataSource(DataState.LOADING))
                }
                .subscribeWith(object : DisposableObserver<NewsResult>() {
                    override fun onComplete() {
                        liveData.postValue(DataSource(DataState.SUCCESS, newsResult))
                    }

                    override fun onNext(t: NewsResult?) {
                        newsResult = t
                    }

                    override fun onError(e: Throwable?) {
                        liveData.postValue(DataSource(DataState.ERROR, throwable = e))
                    }

                })
        )
    }

    fun clearViewModel() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        clearViewModel()
    }
}