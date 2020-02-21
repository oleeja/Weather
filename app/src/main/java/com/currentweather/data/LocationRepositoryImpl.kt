package com.currentweather.data

import android.location.Location
import com.currentweather.data_sources.LastKnownLocationDataSource
import com.currentweather.data_sources.UpdateLocationDataSource
import com.currentweather.domain.LocationRepository

class LocationRepositoryImpl (private val lastKnownLocationDataSource: LastKnownLocationDataSource,
                              private val updateLocationDataSource: UpdateLocationDataSource) : LocationRepository {
    override suspend fun getAppLocation() : Location {
        return try {
            lastKnownLocationDataSource.getLastKnownLocation()
        } catch (e: Exception){
            updateLocationDataSource.getUpdatedLocation()
        }
    }
}