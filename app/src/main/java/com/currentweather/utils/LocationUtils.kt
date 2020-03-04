package com.currentweather.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import java.util.*

fun Location.getDisplayingName(context: Context) : String{
    val addresses: List<Address> =
        Geocoder(context, Locale.getDefault()).getFromLocation(latitude, longitude, 1)
    return if (addresses.isNotEmpty()) {
        with(addresses[0]) {
            locality ?: adminArea ?: countryName ?: "$latitude, $longitude"
        }
    } else ""
}