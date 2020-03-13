package com.currentweather.domain.model.forecast

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Sys(
    @SerializedName("pod")
    val pod: String
)