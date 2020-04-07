package com.doubleb.covid19.di

import com.doubleb.covid19.ui.fragment.*
import org.koin.dsl.module

val fragmentModule = module {
    factory { HomeFragment() }
    factory { WorldFragment() }
    factory { NewsFragment() }
    factory { CareFragment() }
    factory { PreventionFragment() }
    factory { SymptomsFragment() }
}