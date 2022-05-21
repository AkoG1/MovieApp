package com.example.movieapp.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.movieapp.R
import com.example.movieapp.di.*
import com.example.movieapp.presentation.ui.scheduleNotification.ScheduleNotificationDialogFragment
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModules, viewModels, mappers, useCases, database))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.channelID), getString(R.string.app_name), importance)
            channel.description = DESCRIPTION
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val DESCRIPTION = "MovieApp"
    }

}