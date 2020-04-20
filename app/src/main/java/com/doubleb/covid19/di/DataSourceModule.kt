package com.doubleb.covid19.di

import com.doubleb.covid19.repository.data_source.NewsDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {
    single { NewsDataSource(get(), get(named("GoogleNewsApi")), androidContext()) }
}