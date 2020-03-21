package com.doubleb.covid19.di

import com.doubleb.covid19.repository.HomeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { HomeRepository(get()) }
}