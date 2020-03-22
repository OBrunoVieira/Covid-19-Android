package com.doubleb.covid19.di

import com.doubleb.covid19.ui.HomeFragment
import com.doubleb.covid19.ui.WorldFragment
import org.koin.dsl.module

val fragmentModule = module {
    factory { HomeFragment() }
    factory { WorldFragment() }
}