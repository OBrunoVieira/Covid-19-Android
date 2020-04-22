package com.doubleb.covid19.repository.data_source

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.doubleb.covid19.R
import com.doubleb.covid19.model.News
import com.doubleb.covid19.model.NewsResult
import com.doubleb.covid19.network.GoogleNewsDataSource
import com.doubleb.covid19.storage.Key
import com.doubleb.covid19.storage.Preferences
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class NewsDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val googleNewsDataSource: GoogleNewsDataSource,
    private val context: Context
) : PageKeyedDataSource<Int, News>() {

    val liveDataState = MutableLiveData<DataSource<NewsResult>>()

    private val isoList by lazy {
        arrayListOf(
            "ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz",
            "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr",
            "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs",
            "ru", "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za"
        )
    }

    private var newsResult: NewsResult? = null
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        liveDataState.postValue(DataSource(DataState.LOADING))

        compositeDisposable.add(
            googleNewsDataSource.getTopHeadlines(
                pageSize = params.requestedLoadSize,
                country = getCountryIso()
            ).subscribeWith(object : DisposableObserver<NewsResult>() {
                override fun onComplete() {
                    liveDataState.postValue(DataSource(DataState.SUCCESS, newsResult))

                    val articles = newsResult?.articles
                    if (!articles.isNullOrEmpty()) {
                        val totalResults = newsResult?.totalResults ?: 0
                        val pageSize = params.requestedLoadSize
                        val hasNextPage = totalResults >= pageSize && articles.size == pageSize
                        val nextPage = if (hasNextPage) 2 else null
                        callback.onResult(articles, null, nextPage)
                    }

                }

                override fun onNext(t: NewsResult?) {
                    newsResult = t
                }

                override fun onError(e: Throwable?) {
                    liveDataState.postValue(DataSource(DataState.ERROR, newsResult, e))
                    retryAction { loadInitial(params, callback) }
                }

            })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        liveDataState.postValue(DataSource(DataState.LOADING))

        compositeDisposable.add(
            googleNewsDataSource.getTopHeadlines(
                params.key,
                params.requestedLoadSize,
                getCountryIso()
            ).subscribeWith(object : DisposableObserver<NewsResult>() {
                override fun onComplete() {
                    liveDataState.postValue(DataSource(DataState.SUCCESS, newsResult))

                    val articles = newsResult?.articles
                    if (!articles.isNullOrEmpty()) {
                        val totalResults = newsResult?.totalResults ?: 0
                        val pageSize = params.requestedLoadSize
                        val hasNextPage = totalResults >= pageSize && articles.size == pageSize
                        val nextPage = if (hasNextPage) params.key + 1 else null

                        callback.onResult(articles, nextPage)
                    }
                }

                override fun onNext(t: NewsResult?) {
                    newsResult = t
                }

                override fun onError(e: Throwable?) {
                    liveDataState.postValue(DataSource(DataState.ERROR, newsResult, e))
                    retryAction { loadAfter(params, callback) }
                }

            })
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, News>
    ) {
    }

    fun retry() {
        retryCompletable?.let {
            compositeDisposable.add(
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    fun clearDisposables() {
        compositeDisposable.clear()
    }

    private fun getCountryIso() = run {
        val defaultIso = recoverCountryIso().toLowerCase(Locale.getDefault())
        return@run if (defaultIso.isNotEmpty() && isoList.contains(defaultIso)) {
            defaultIso
        } else {
            ""
        }
    }

    private fun recoverCountryIso() = Preferences.readStringValue(context, Key.COUNTRY_ISO)
        ?: run {
            val defaultValue = context.getString(R.string.country_iso)
            Preferences.writeValue(
                context,
                Key.COUNTRY_ISO, defaultValue
            )

            return defaultValue
        }

    private inline fun retryAction(crossinline method: () -> Unit) {
        retryCompletable = Completable.fromAction {
            method()
        }
    }
}