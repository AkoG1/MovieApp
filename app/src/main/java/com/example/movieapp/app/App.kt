package com.example.movieapp.app

import android.app.Application
import com.example.movieapp.di.appModules
import com.example.movieapp.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModules, viewModels))
        }
    }

}