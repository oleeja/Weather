package com.currentweather.domain.model.forecast

import com.google.gson.annotations.SerializedName


data class Sys(
    @SerializedName("pod")
    val pod: String
)