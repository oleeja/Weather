package com.currentweather.data_sources

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LastKnownLocationDataSource(private val fusedLocationClient: FusedLocationProviderClient) {
    suspend fun getLastKnownLocation() : Location{
        return suspendCoroutine {
            fusedLocationClient.lastLocation
                .addOnFailureListener { exeption->
                    throw exeption
                }
                .addOnSuccessListener { location : Location? ->
                    it.resume(location ?: throw Exception())
                }
        }
    }
}