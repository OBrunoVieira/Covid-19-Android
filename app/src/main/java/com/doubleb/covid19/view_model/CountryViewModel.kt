package com.doubleb.covid19.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleb.covid19.model.CountryData
import com.doubleb.covid19.repository.CountryRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CountryViewModel(
    private val countryRepository: CountryRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val liveData = MutableLiveData<DataSource<CountryData>>()

    private var subscription: Disposable? = null
    private var countryData: CountryData? = null

    fun getByCountry(country: String) {
        subscription = Observable.interval(0, 2, TimeUnit.MINUTES).map {
            compositeDisposable.add(
                countryRepository.getCountry(country)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        liveData.postValue(DataSource(DataState.LOADING))
                    }
                    .subscribeWith(object : DisposableObserver<CountryData>() {
                        override fun onComplete() {
                            liveData.postValue(DataSource(DataState.SUCCESS, countryData))
                        }

                        override fun onNext(t: CountryData?) {
                            countryData = t
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