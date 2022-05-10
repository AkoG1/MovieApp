package com.example.movieapp.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.widget.Toast
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
            val channel = NotificationChannel(channelId, NAME, importance)
            channel.description = ScheduleNotificationDialogFragment.DESCRIPTION
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        } else {
            Toast.makeText(applicationContext, WARNING , Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val channelId = "Reminder"
        const val NAME = "MovieApp Channel"
        const val WARNING = "Notifications will not work on your device"
    }

}