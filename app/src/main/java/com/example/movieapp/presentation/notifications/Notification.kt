package com.example.movieapp.presentation.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.movieapp.R
import com.example.movieapp.presentation.MainActivity

const val notificationId = 1
const val channelId = "Reminder"
const val titleExtra = "TitleExtra"
const val messageExtra = "MessageExtra"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.mobile_navigation)
            //navigation is just an example(in progress...)
            .setDestination(R.id.navigation_notifications)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification.build())
    }

}