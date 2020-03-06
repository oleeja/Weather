package com.currentweather.data

import android.location.Location
import com.currentweather.domain.LocationRepository
import com.google.android.gms.maps.model.LatLng

class OnGoingLocationRepositoryImpl : LocationRepository {
    override suspend fun getLocation(): Location {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveLocation(latLng: LatLng) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}