package com.currentweather.domain

import android.location.Location
import com.google.android.gms.maps.model.LatLng

interface LocationRepository {
    suspend fun getLocation() : Location
    suspend fun saveLocation(latLng: LatLng)
}