package com.currentweather.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.location.Location
import android.os.Build
import com.currentweather.R
import com.currentweather.ui.launch.LaunchActivity

class NotificationUtils(val context: Context) {
    @Suppress("DEPRECATION")
    fun createCustomNotification(smallIcon: Icon, bigIcon: Bitmap, title: String, subtitle: String, notificationId: Int, locationInfo: Location) {
        val channelId = context.getString(R.string.default_ongoing_channel_id)
        val notification =
            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
                context,
                channelId
            )
            else Notification.Builder(context))
                .setOngoing(true)
                .setVibrate(null)
                .setContentIntent(
                    PendingIntent.getActivity(context, 0,
                        Intent(context, LaunchActivity::class.java).apply {
                            putExtra(NOTIFICATION_TAG, locationInfo)
                        }, PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        setSmallIcon(smallIcon)
                    } else {
                        setSmallIcon(R.mipmap.ic_launcher)
                    }
                }
                .setLargeIcon(bigIcon)
                .setContentTitle(title)
                .setContentText(subtitle)
                .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.default_ongoing_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance)
            channel.vibrationPattern = longArrayOf(0)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(notificationId, notification)
    }

    companion object {
        val SPLASH_ACTIVITY_NAME = "com.wunderground.android.weather.ui.splash.WeatherHomeActivity"
        val NOTIFICATION_TAG = "ongoingNotification"
    }
}