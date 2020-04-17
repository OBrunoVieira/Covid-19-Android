package com.doubleb.covid19.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleb.covid19.model.WorldData
import com.doubleb.covid19.repository.WorldRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class OverviewViewModel (
    private val worldRepository: WorldRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel(){

    val liveDataWorldCases = MutableLiveData<DataSource<WorldData>>()

    private var worldData: WorldData? = null

    fun getWorldCases() {
        compositeDisposable.add(
            worldRepository.getWorldCases()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                    liveDataWorldCases.postValue(DataSource(DataState.LOADING))
                }
                .subscribeWith(object : DisposableObserver<WorldData>() {
                    override fun onComplete() {
                        liveDataWorldCases.postValue(DataSource(DataState.SUCCESS, worldData))
                    }

                    override fun onNext(t: WorldData?) {
                        worldData = t
                    }

                    override fun onError(e: Throwable?) {
                        liveDataWorldCases.postValue(DataSource(DataState.ERROR, throwable = e))
                    }

                })
        )
    }

    fun clearViewModel() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}