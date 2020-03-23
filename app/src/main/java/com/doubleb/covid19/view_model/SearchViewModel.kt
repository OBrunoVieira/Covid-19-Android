package com.doubleb.covid19.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleb.covid19.model.Country
import com.doubleb.covid19.repository.SearchRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val liveData = MutableLiveData<DataSource<List<Country>>>()
    var list: List<Country>? = null

    fun getCountryNames() {
        compositeDisposable.add(
            searchRepository.getCountryNames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveData.postValue(DataSource(DataState.LOADING))
                }
                .subscribeWith(object : DisposableObserver<List<Country>>() {
                    override fun onComplete() {
                        liveData.postValue(
                            DataSource(
                                DataState.SUCCESS,
                                list
                            )
                        )
                    }

                    override fun onNext(t: List<Country>?) {
                        list = t
                    }

                    override fun onError(e: Throwable?) {
                        liveData.postValue(DataSource(DataState.ERROR, throwable = e))
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}