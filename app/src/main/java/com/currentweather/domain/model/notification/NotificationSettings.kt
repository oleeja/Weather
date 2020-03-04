package com.currentweather.domain.model.notification

import android.content.Context

/**
 * Status bar notification view settings implementation.
 *
 * @author Oleh Nahornyi
 */
class NotificationSettings(context: Context){

    val prefs =
        context.getSharedPreferences(PREF_ONGOING_NOTIFICATION_SETTINGS_FILENAME, Context.MODE_PRIVATE)


    var isStatusBarNotificationEnable: Boolean
        /**
         * @return true if status bar notifications are enabled
         */
        get() = prefs.getBoolean(NOTIFICATION_ENABLE_PREF_KEY, false)
        /**
         * Sets if status bar notifications are enabled to settings.
         *
         * @param isNotificationEnable true if enabled
         * @see .isStatusBarNotificationEnable
         */
        set(isNotificationEnable) = prefs.edit().putBoolean(NOTIFICATION_ENABLE_PREF_KEY, isNotificationEnable).apply()

    var notificationRefreshInterval: NotificationRefreshInterval
        /**
         * @return notification refresh interval
         */
        get() = NotificationRefreshInterval.valueOf(prefs.getInt(REFRESH_INTERVAL_PREF_KEY, -1))
        /**
         * Alters current refresh interval settings.
         *
         * @param refreshInterval refresh interval to set
         * @see .getNavigationType
         */
        set(refreshInterval) = prefs.edit().putInt(REFRESH_INTERVAL_PREF_KEY, refreshInterval.id).apply()


    companion object {
        const val PREF_ONGOING_NOTIFICATION_SETTINGS_FILENAME = "prefs_app_status_bar_view"

        private const val NOTIFICATION_ENABLE_PREF_KEY = "enable_notifications"
        private const val REFRESH_INTERVAL_PREF_KEY = "refresh_interval"
    }
}
