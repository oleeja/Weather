package com.currentweather.domain.model.forecast

import com.currentweather.domain.model.Clouds
import com.currentweather.domain.model.Main
import com.currentweather.domain.model.Weather
import com.currentweather.domain.model.Wind
import com.google.gson.annotations.SerializedName

data class ForecastThreeHours(
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("rain")
    val rain: Rain,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
)