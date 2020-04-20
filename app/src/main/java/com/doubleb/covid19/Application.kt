package com.doubleb.covid19

import android.app.Application
import com.doubleb.covid19.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            androidLogger()
            fragmentFactory()
            modules(viewModelModule, repositoryModule, networkModule, fragmentModule, dataSourceModule)
        }
    }
}