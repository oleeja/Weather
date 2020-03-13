package com.currentweather.domain.model

import androidx.room.Entity

@Entity
data class Unit(
    val name: String,
    val key: String,
    val temperatureValue: String,
    val speedValue: String,
    val pressureValue: String,
    var chosen: Boolean = false
)