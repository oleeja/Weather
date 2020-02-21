package com.currentweather.domain.model

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("grnd_level")
    val grndLevel: Int?,
    @SerializedName("sea_level")
    val seaLevel: Int?,
    @SerializedName("temp_kf")
    val tempKf: Double?
)