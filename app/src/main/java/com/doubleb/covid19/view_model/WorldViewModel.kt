package com.doubleb.covid19.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleb.covid19.model.BaseData
import com.doubleb.covid19.repository.WorldRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class WorldViewModel(
    private val worldRepository: WorldRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val liveDataWorldCases = MutableLiveData<DataSource<List<BaseData>>>()
    val liveDataCasesByCountry = MutableLiveData<DataSource<List<BaseData>>>()

    private var baseChartList: List<BaseData>? = null
    private var baseDataList: List<BaseData>? = null

    fun getWorldCases() {
        compositeDisposable.add(
            worldRepository.getWorldCases()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                    liveDataWorldCases.postValue(DataSource(DataState.LOADING))
                }
                .subscribeWith(object : DisposableObserver<List<BaseData>>() {
                    override fun onComplete() {
                        liveDataWorldCases.postValue(DataSource(DataState.SUCCESS, baseChartList))
                    }

                    override fun onNext(t: List<BaseData>?) {
                        baseChartList = t
                    }

                    override fun onError(e: Throwable?) {
                        liveDataWorldCases.postValue(DataSource(DataState.ERROR, throwable = e))
                    }

                })
        )
    }

    fun getCasesByCountries() {
        compositeDisposable.add(
            worldRepository.getCasesByCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveDataCasesByCountry.postValue(DataSource(DataState.LOADING))
                }
                .subscribeWith(object : DisposableObserver<List<BaseData>>() {
                    override fun onComplete() {
                        liveDataCasesByCountry.postValue(
                            DataSource(
                                DataState.SUCCESS,
                                baseDataList
                            )
                        )
                    }

                    override fun onNext(t: List<BaseData>?) {
                        baseDataList = t
                    }

                    override fun onError(e: Throwable?) {
                        liveDataCasesByCountry.postValue(DataSource(DataState.ERROR, throwable = e))
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