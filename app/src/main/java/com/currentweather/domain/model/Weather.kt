package com.currentweather.domain.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Weather(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)