package com.currentweather.data_sources

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LastKnownLocationDataSource(private val fusedLocationClient: FusedLocationProviderClient) {
    suspend fun getLastKnownLocation() : Location{
        return suspendCoroutine {
            fusedLocationClient.lastLocation
                .addOnFailureListener { exception->
                    it.resumeWithException(exception)
                }
                .addOnSuccessListener { location : Location? ->
                    if(location != null) it.resume(location) else it.resumeWithException(Exception())
                }
        }
    }
}