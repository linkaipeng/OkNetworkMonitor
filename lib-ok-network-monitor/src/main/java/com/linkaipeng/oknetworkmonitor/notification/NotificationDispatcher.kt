package com.linkaipeng.oknetworkmonitor.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import androidx.core.app.NotificationCompat
import com.linkaipeng.oknetworkmonitor.R

/**
 * Created by linkaipeng on 2020/6/5.
 *
 */
object NotificationDispatcher {


    private const val CHANNEL_ID = "TimeoutNotificationChannel"

    fun showNotification(
            context: Context,
            contentTitle: CharSequence,
            contentText: CharSequence,
            pendingIntent: PendingIntent?,
            notificationId: Int
    ) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentText(contentText)
                .setContentTitle(contentTitle)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        val notification =
                buildNotification(context, builder)
        val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    private fun buildNotification(
            context: Context,
            builder: NotificationCompat.Builder
    ): Notification {
        builder.setSmallIcon(R.drawable.ic_notification)
                .setWhen(System.currentTimeMillis())

        if (SDK_INT >= O) {
            val channelName = "TimeoutNotification"
            val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            var notificationChannel: NotificationChannel? =
                    notificationManager.getNotificationChannel(CHANNEL_ID)
            if (notificationChannel == null) {
                notificationChannel = NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(notificationChannel)
            }
            builder.setChannelId(CHANNEL_ID)
        }
        return builder.build()
    }

}