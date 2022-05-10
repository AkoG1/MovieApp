package com.example.movieapp.presentation.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.GROUP_ALERT_ALL
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.example.movieapp.R
import com.example.movieapp.presentation.MainActivity

const val reminderNotificationIdExtra = "1"
const val channelId = "Reminder"
const val titleExtra = "TitleExtra"
const val messageExtra = "MessageExtra"
const val idExtra = ""
const val GROUP = "watchReminder"
const val ID = "id"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.movieDetailsFragment)
            .setArguments(
                bundleOf(
                    ID to intent.getStringExtra(idExtra)
                )
            )
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setVibrate(longArrayOf(VIBRATE_1_SEC, VIBRATE_1_SEC, VIBRATE_1_SEC))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setGroup(GROUP)
            .setGroupAlertBehavior(GROUP_ALERT_ALL)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val uniqueNotificationId = intent.getIntExtra(reminderNotificationIdExtra, 1)
        manager.notify(uniqueNotificationId, notification)
    }

    companion object {
        private const val VIBRATE_1_SEC = 1000L
    }
}