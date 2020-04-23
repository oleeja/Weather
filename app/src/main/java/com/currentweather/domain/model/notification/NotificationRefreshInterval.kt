package com.currentweather.domain.model.notification

import android.content.Context
import com.currentweather.R


/**
 * Represents widget refresh interval.
 *
 * @author Oleh Nahornyi
 */
sealed class NotificationRefreshInterval constructor(
    val id: Int,
    val intervalMillis: Long,
    private val textResId: Int
) {

    class RI_30_MINUTES : NotificationRefreshInterval(0, 30 * 60000, R.string.ri_30minutes)
    class RI_1_HOUR : NotificationRefreshInterval(1, 60 * 60000, R.string.ri_1hour)
    class RI_2_HOURS : NotificationRefreshInterval(2, 120 * 60000, R.string.ri_2hours)
    class RI_4_HOURS: NotificationRefreshInterval(3, 240 * 60000, R.string.ri_4hours)
    class RI_ONCE_DAILY: NotificationRefreshInterval(4, 24 * 60 * 60000, R.string.ri_once_daily)


    /**
     * Returns text of this widget refresh interval(uses device locale and is not the same as [.toString] or
     * [.name]).
     *
     * @param context application context, cannot be `null`
     *
     * @return text of this widget refresh interval
     */
    fun getText(context: Context): String {
        return context.getString(textResId)
    }

    companion object {


        val notificationIntervalsList = arrayListOf(RI_30_MINUTES(), RI_1_HOUR(), RI_2_HOURS(), RI_4_HOURS(), RI_ONCE_DAILY())

        /**
         * Returns [NotificationRefreshInterval] that corresponds to the given ID.
         *
         * @param id ID of widget refresh interval
         *
         * @return [NotificationRefreshInterval] that corresponds to the given ID,
         * returns [.RI_30_MINUTES] by default
         */
        fun valueOf(id: Int): NotificationRefreshInterval {
            for (interval in notificationIntervalsList) {
                if (id == interval.id) {
                    return interval
                }
            }
            return RI_30_MINUTES()
        }

        /**
         * Returns [NotificationRefreshInterval] that corresponds to the given
         * refresh interval in milliseconds.
         *
         * @param intervalMillis refresh interval in milliseconds
         *
         * @return [NotificationRefreshInterval] that corresponds to the given
         * refresh interval in milliseconds, returns [.RI_30_MINUTES] by default
         */
        fun valueOf(intervalMillis: Long): NotificationRefreshInterval {
            for (interval in notificationIntervalsList) {
                if (intervalMillis == interval.intervalMillis) {
                    return interval
                }
            }
            return RI_30_MINUTES()
        }

        /**
         * Returns [NotificationRefreshInterval] that corresponds to the given
         * refresh interval text.
         *
         * @param context application context, cannot be `null`
         * @param text    refresh interval text
         *
         * @return [NotificationRefreshInterval] that corresponds to the given
         * refresh interval text, returns [.RI_30_MINUTES] by default
         */
        fun valueOf(context: Context, text: String): NotificationRefreshInterval {
            for (interval in notificationIntervalsList) {
                if (interval.getText(context) == text) {
                    return interval
                }
            }
            return RI_30_MINUTES()
        }
    }
}