package com.doubleb

import android.app.Application
import com.doubleb.covid19.di.networkModule
import com.doubleb.covid19.di.repositoryModule
import com.doubleb.covid19.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            androidLogger()
            modules(viewModelModule, repositoryModule, networkModule)
        }
    }
}