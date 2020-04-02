package com.currentweather.domain.model.forecast

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ForecastThreeHoursModel(
    @PrimaryKey
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<ForecastThreeHours>,
    @SerializedName("message")
    val message: Int,
    val dt: Long = System.currentTimeMillis()
)