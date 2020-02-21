package com.currentweather.domain.model.forecast


import com.google.gson.annotations.SerializedName

data class ForecastThreeHoursModel(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<ForecastThreeHours>,
    @SerializedName("message")
    val message: Int
)