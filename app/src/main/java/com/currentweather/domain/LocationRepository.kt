package com.currentweather.domain

import android.location.Location

interface LocationRepository {
    suspend fun getAppLocation() : Location
}