package com.currentweather.data

import android.location.Location
import com.currentweather.data_sources.AppLocationDataSource
import com.currentweather.data_sources.LastKnownLocationDataSource
import com.currentweather.data_sources.UpdateLocationDataSource
import com.currentweather.domain.LocationRepository
import com.google.android.gms.maps.model.LatLng

class LocationRepositoryImpl (private val lastKnownLocationDataSource: LastKnownLocationDataSource,
                              private val updateLocationDataSource: UpdateLocationDataSource,
                              private val appLocationDataSource: AppLocationDataSource) : LocationRepository {
    override suspend fun getAppLocation() : Location {
        return try {
            appLocationDataSource.getLocation() ?: lastKnownLocationDataSource.getLastKnownLocation()
        }catch (e: Exception){
            if(e is SecurityException) throw e
            updateLocationDataSource.getUpdatedLocation()
        }
    }

    override suspend fun saveAppLocation(latLng: LatLng){
        appLocationDataSource.saveLocation(latLng)
    }
}