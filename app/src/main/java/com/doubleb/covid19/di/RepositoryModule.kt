package com.doubleb.covid19.di

import com.doubleb.covid19.repository.CountryRepository
import com.doubleb.covid19.repository.SearchRepository
import com.doubleb.covid19.repository.WorldRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CountryRepository(get()) }
    single { WorldRepository(get()) }
    single { SearchRepository(get()) }
}