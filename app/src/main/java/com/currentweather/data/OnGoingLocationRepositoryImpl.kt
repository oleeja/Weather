package com.currentweather.data

import android.location.Location
import android.location.LocationManager
import com.currentweather.data_sources.AppLocationDataSource
import com.currentweather.data_sources.LastKnownLocationDataSource
import com.currentweather.domain.LocationRepository
import com.currentweather.domain.model.notification.NotificationSettings
import com.google.android.gms.maps.model.LatLng

class OnGoingLocationRepositoryImpl(private val notificationSettings: NotificationSettings,
                                    private val appLocationDataSource: AppLocationDataSource,
                                    private val lastKnownLocationDataSource: LastKnownLocationDataSource
) : LocationRepository {
    override suspend fun getLocation() =
        notificationSettings.notificationLocation
            ?: appLocationDataSource.getLocation()
            ?: lastKnownLocationDataSource.getLastKnownLocation()

    override suspend fun saveLocation(latLng: LatLng) {
        notificationSettings.notificationLocation = Location(LocationManager.PASSIVE_PROVIDER).apply {
            latitude = latLng.latitude
            longitude = latLng.longitude
        }
    }
}