package com.currentweather.data_sources

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng

class AppLocationDataSource(context: Context) {
    private val locationPreferences: SharedPreferences =
        context.getSharedPreferences("LOCATION_PREFERENCES", Context.MODE_PRIVATE)

    private val APP_LOCATION = "APP_LOCATION"

    suspend fun saveLocation(latLng: LatLng){
        locationPreferences.edit().putString(APP_LOCATION, "${latLng.latitude},${latLng.longitude}").apply()
    }

    suspend fun getLocation(): Location?{
        return with(locationPreferences.getString(APP_LOCATION, null)){
            if(this != null) Location(LocationManager.PASSIVE_PROVIDER).apply {
                val latLng = this@with.split(",")
                latitude = latLng.first().toDouble()
                longitude = latLng.last().toDouble()
            } else null
        }
    }
}