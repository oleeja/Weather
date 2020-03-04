package com.currentweather.domain



/**
 * Handler that allow to start ongoing notification background refreshing
 *
 * @author Oleh Nahornyi
 */
interface OnGoingRefreshRepository {
    /**
     * Starts background refreshing
     */
    suspend fun enableNotification()
}