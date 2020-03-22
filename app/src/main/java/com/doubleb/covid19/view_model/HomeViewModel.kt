package com.doubleb.covid19.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleb.covid19.model.Result
import com.doubleb.covid19.repository.HomeRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var subscription: Disposable? = null

    val liveData = MutableLiveData<DataSource<Result>>()

    fun getByCountry() {
        subscription = Observable.interval(0, 2, TimeUnit.MINUTES).map {
           compositeDisposable.add(
                homeRepository.getCountry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        liveData.postValue(DataSource(DataState.LOADING))
                    }
                    .subscribeWith(object : DisposableObserver<Result>() {
                        override fun onComplete() {
                            liveData.postValue(DataSource(DataState.SUCCESS, liveData.value?.data))
                        }

                        override fun onNext(t: Result?) {
                            liveData.value = DataSource(DataState.SUCCESS, t)
                        }

                        override fun onError(e: Throwable?) {
                            liveData.postValue(DataSource(DataState.ERROR, throwable = e))
                        }

                    })
            )
        }.subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        subscription?.dispose()
    }
}