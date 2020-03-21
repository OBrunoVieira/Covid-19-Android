package com.doubleb.covid19.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleb.covid19.repository.HomeRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val liveData = MutableLiveData<DataSource<Any>>()

    fun getByCountry() {
        compositeDisposable.add(
            homeRepository.getCountry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveData.postValue(DataSource(DataState.LOADING))
                }
                .subscribeWith(object : DisposableObserver<Any>() {
                    override fun onComplete() {
                        liveData.postValue(DataSource(DataState.SUCCESS, liveData.value?.data))
                    }

                    override fun onNext(t: Any?) {
                        liveData.value = DataSource(DataState.SUCCESS, t)
                    }

                    override fun onError(e: Throwable?) {
                        liveData.postValue(DataSource(DataState.ERROR, throwable = e))
                    }

                })

        )
    }
}