package com.example.movieapp.presentation.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.movieapp.R
import com.example.movieapp.presentation.MainActivity

const val notificationId = 1
const val channelId = "MovieAppChannel"
const val titleExtra = "TitleExtra"
const val messageExtra = "MessageExtra"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
//
//        val intent = Intent(context, MainActivity:: class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)


        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)
    }

}