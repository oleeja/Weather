package com.currentweather.ui.notification_ui

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class RefreshButtonListener : BroadcastReceiver(), KoinComponent{

    private val notificationUiHandler: OnGoingNotificationHandler by inject()

    @SuppressLint("CheckResult")
    override fun onReceive(context: Context, intent: Intent) {
        GlobalScope.launch {
            notificationUiHandler.showNotification()
        }
    }
}