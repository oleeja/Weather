package com.currentweather.data_sources

import com.currentweather.domain.model.Unit

class UnitsDataSource {

    fun getUnits() : List<Unit>{
        return arrayListOf(
            Unit("Standard", "", "Kelvin", "meter/sec", "hPa"),
            Unit("Metric", "", "Celsius", "meter/sec", "hPa"),
            Unit("Imperial", "", "Fahrenheit", "meter/sec", "hPa")
        )
    }
}