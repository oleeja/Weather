package com.wunderground.android.weather.app.status_bar_notifications_manager

import android.content.Context
import com.currentweather.ui.notification_ui.OnGoingNotificationDataModel
import com.google.gson.Gson

class NotificationDataCache (val context: Context) {

    val prefs =
        context.getSharedPreferences(PREF_ONGOING_NOTIFICATION_CACHE_FILENAME, Context.MODE_PRIVATE)

    fun saveNotificationData(onGoingNotificationDataModel: OnGoingNotificationDataModel) {
        prefs.edit().putString(NOTIFICATION_DATA_KEY, Gson().toJson(onGoingNotificationDataModel)).apply()
    }

    fun getCachedNotificationData() : OnGoingNotificationDataModel {
        return Gson().fromJson(prefs.getString(NOTIFICATION_DATA_KEY, ""), OnGoingNotificationDataModel::class.java)
    }


    companion object {
        const val PREF_ONGOING_NOTIFICATION_CACHE_FILENAME = "prefsAppOnGoingNotificationCache"
        private val NOTIFICATION_DATA_KEY = "notificationData"
    }
}