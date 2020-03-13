package com.currentweather.domain.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Coord(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)