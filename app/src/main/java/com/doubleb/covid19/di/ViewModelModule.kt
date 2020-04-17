package com.doubleb.covid19.di

import com.doubleb.covid19.view_model.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { CompositeDisposable() }

    // Home Section ================================================================================
    viewModel { HomeViewModel(get(), get()) }

    // Country Details Section =====================================================================
    viewModel { CountryViewModel(get(), get()) }

    // Search Section ==============================================================================
    viewModel { SearchViewModel(get(), get()) }

    // World Section ===============================================================================
    viewModel { OverviewViewModel(get(), get()) }
    viewModel { CountriesViewModel(get(), get()) }
}