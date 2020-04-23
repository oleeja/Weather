package com.currentweather.ui.notification_ui

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import com.currentweather.domain.model.notification.NotificationSettings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

/**
 * Receiver that catch moment after rebooting device and restore notification if it was enabled
 */
class NotificationsBootCompleteReceiver : BroadcastReceiver(), KoinComponent {

    private val scopeInstance =
        getKoin().createScope(notificationBackgroundScopeId, named<NotificationUiHandler>())
    private val notificationUiHandler = scopeInstance.get<OnGoingNotificationHandler>()

    private val notificationSettings: NotificationSettings by inject()

    @SuppressLint("CheckResult")
    override fun onReceive(context: Context, intent: Intent) {
        if (ACTION_BOOT_COMPLETED == intent.action) {
            if (notificationSettings.isStatusBarNotificationEnable) {
                GlobalScope.launch {
                    notificationUiHandler.showNotification()
                }
            }
        }
    }
}