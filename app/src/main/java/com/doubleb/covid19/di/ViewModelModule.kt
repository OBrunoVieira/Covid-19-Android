package com.doubleb.covid19.di

import com.doubleb.covid19.view_model.CountryViewModel
import com.doubleb.covid19.view_model.HomeViewModel
import com.doubleb.covid19.view_model.SearchViewModel
import com.doubleb.covid19.view_model.WorldViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { CompositeDisposable() }

    viewModel { CountryViewModel(get(), get()) }
    viewModel { WorldViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
}