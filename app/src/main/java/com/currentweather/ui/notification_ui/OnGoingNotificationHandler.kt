package com.currentweather.ui.notification_ui

/**
 * Handler that allows to show and dismiss ongoing notification
 *
 * @author Oleh Nahornyi
 */
interface OnGoingNotificationHandler {
    /**
     * Shows notification
     */
    suspend fun showNotification()

    /**
     * Dismisses notification
     */
    suspend fun dismissNotification()

    /**
     * @return flag that shows is ongoing notification visible
     */
    val isNotificationEnable: Boolean
}