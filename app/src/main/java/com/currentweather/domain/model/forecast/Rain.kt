package com.currentweather.domain.model.forecast

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Rain(
    @SerializedName("3h")
    val h: Double
)