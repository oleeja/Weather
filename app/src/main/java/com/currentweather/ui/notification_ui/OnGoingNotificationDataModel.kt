package com.currentweather.ui.notification_ui

import android.location.Location


data class OnGoingNotificationDataModel(
        val temperature: Int,
        val tempMax: Int,
        val tempMin: Int,
        val humidity: Int,
        val iconCode: String,
        val locationInfo: Location,
        var updatedTime: Long? = null)