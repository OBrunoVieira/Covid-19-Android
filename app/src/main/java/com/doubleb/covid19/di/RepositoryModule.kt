package com.doubleb.covid19.di

import com.doubleb.covid19.repository.CountryRepository
import com.doubleb.covid19.repository.NewsRepository
import com.doubleb.covid19.repository.SearchRepository
import com.doubleb.covid19.repository.WorldRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    // Country ======================================================================================
    single { CountryRepository(get()) }

    // World =======================================================================================
    single { WorldRepository(get()) }

    // Search ======================================================================================
    single { SearchRepository(get()) }

    // News ========================================================================================
    single { NewsRepository(get()) }
}