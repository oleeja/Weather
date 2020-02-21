package com.currentweather.data_sources

import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UpdateLocationDataSource(private val fusedLocationClient: FusedLocationProviderClient) {
    private var locationCallback : LocationCallback? = null

    suspend fun getUpdatedLocation() : Location{
        return suspendCoroutine {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                    for (location in locationResult.locations){
                        it.resume(location)
                    }
                }
            }
            fusedLocationClient.requestLocationUpdates(LocationRequest.create()?.apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }, locationCallback, Looper.getMainLooper())
        }
    }

    fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
    }
}