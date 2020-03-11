package com.currentweather.ui.notification_ui

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named

class StatusBarNotificationWorkManager(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters), KoinComponent {

    private val scopeInstance =
        getKoin().createScope(notificationBackgroundScopeId, named<NotificationUiHandler>())
    private val notificationUiHandler = scopeInstance.get<OnGoingNotificationHandler>()

    override suspend fun doWork(): Result {
        notificationUiHandler.showNotification()
        return Result.success(workDataOf(TAG to null))
    }

    companion object {
        val TAG = StatusBarNotificationWorkManager::class.java.simpleName
    }
}