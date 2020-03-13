package com.currentweather.domain.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Clouds(
    @SerializedName("all")
    val all: Int
)