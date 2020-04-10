package com.currentweather.data_sources

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.currentweather.domain.OnGoingRefreshRepository
import com.currentweather.domain.model.notification.NotificationRefreshInterval
import com.currentweather.domain.model.notification.NotificationSettings
import com.currentweather.ui.notification_ui.StatusBarNotificationWorkManager
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * The data manager, refreshing work manager with new settings.
 *
 * @author Oleh Nahornyi
 */
class StatusBarRefreshRepositoryImpl(
    val context: Context,
    val notificationSettings: NotificationSettings
) : OnGoingRefreshRepository {

    private val INTERVAL_30_MINUTES = 30
    private val INTERVAL_60_MINUTES = 60
    private val INTERVAL_120_MINUTES = 120
    private val INTERVAL_240_MINUTES = 240
    private val INTERVAL_1440_MINUTES = 1440

    private val INTERVAL_WINDOW = 15

    private val INTERVAL_24_HOUR_REFRESH_TIME = Pair(7, 0)

    private val TAG = StatusBarRefreshRepositoryImpl::class.java.simpleName

    @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
    override suspend fun enableNotification() {
        WorkManager.getInstance(context).apply {
            if (!getWorkInfosByTag(StatusBarNotificationWorkManager.TAG).isCancelled ||
                !getWorkInfosByTag(StatusBarNotificationWorkManager.TAG).isDone
            )
                cancelAllWorkByTag(StatusBarNotificationWorkManager.TAG)


            if (notificationSettings.isStatusBarNotificationEnable) {
                val flexInterval = when (notificationSettings.notificationRefreshInterval) {
                    is NotificationRefreshInterval.RI_30_MINUTES -> INTERVAL_30_MINUTES
                    is NotificationRefreshInterval.RI_1_HOUR -> INTERVAL_60_MINUTES
                    is NotificationRefreshInterval.RI_2_HOURS -> INTERVAL_120_MINUTES
                    is NotificationRefreshInterval.RI_4_HOURS -> INTERVAL_240_MINUTES
                    is NotificationRefreshInterval.RI_ONCE_DAILY -> INTERVAL_1440_MINUTES
                }

                val repeatInterval = flexInterval + INTERVAL_WINDOW

                val request = PeriodicWorkRequest.Builder(
                    StatusBarNotificationWorkManager::class.java,
                    repeatInterval.toLong(),
                    TimeUnit.MINUTES,
                    INTERVAL_WINDOW.toLong(),
                    TimeUnit.MINUTES
                )
                    .addTag(StatusBarNotificationWorkManager.TAG)
                    .apply {
                        if (flexInterval == INTERVAL_1440_MINUTES) {
                            val currentTime = Calendar.getInstance()
                            val nextDayTime = Calendar.getInstance().apply {
                                add(Calendar.DAY_OF_MONTH, 1)
                                set(Calendar.HOUR_OF_DAY, INTERVAL_24_HOUR_REFRESH_TIME.first)
                                set(Calendar.MINUTE, INTERVAL_24_HOUR_REFRESH_TIME.second)
                            }

                            val delayInMillis = nextDayTime.timeInMillis - currentTime.timeInMillis
                            setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
                        }
                    }
                    .build()

                enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.KEEP, request)
            }
        }
    }
}