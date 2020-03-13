package com.currentweather.domain.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Wind(
    @SerializedName("deg")
    val deg: Double,
    @SerializedName("speed")
    val speed: Double
)