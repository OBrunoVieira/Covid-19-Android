package com.doubleb.covid19.di

import com.doubleb.covid19.repository.HomeRepository
import com.doubleb.covid19.repository.SearchRepository
import com.doubleb.covid19.repository.WorldRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.dsl.module

val repositoryModule = module {
    single { HomeRepository(get()) }
    single { WorldRepository(get()) }
    single { SearchRepository(get()) }
}