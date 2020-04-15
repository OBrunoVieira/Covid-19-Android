package com.doubleb.covid19.di

import com.doubleb.covid19.ui.fragment.*
import org.koin.dsl.module

val fragmentModule = module {
    // Home Section ================================================================================
    factory { HomeFragment() }

    // World Section ===============================================================================
    factory { WorldFragment() }
    factory { OverviewFragment() }
    factory { CountriesFragment() }

    // News Section ================================================================================
    factory { NewsFragment() }

    // Care Section ================================================================================
    factory { CareFragment() }
    factory { PreventionFragment() }
    factory { SymptomsFragment() }
}