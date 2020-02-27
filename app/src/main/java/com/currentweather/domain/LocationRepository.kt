package com.currentweather.domain

import android.location.Location
import com.google.android.gms.maps.model.LatLng

interface LocationRepository {
    suspend fun getAppLocation() : Location
    suspend fun saveAppLocation(latLng: LatLng)
}