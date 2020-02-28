package com.currentweather.data_sources

import android.content.Context
import android.content.SharedPreferences
import com.currentweather.domain.model.Unit
import com.google.gson.Gson


class UnitsDataSource(context: Context) {

    private val unitPreferences: SharedPreferences =
        context.getSharedPreferences("UNITS_PREFERENCES", Context.MODE_PRIVATE)

    private val APP_UNIT = "APP_UNIT"

    private val unitList = arrayListOf(
        Unit("Standard", "", "Kelvin", "met/s", "hPa"),
        Unit("Metric", "metric", "Celsius", "met/s", "hPa"),
        Unit("Imperial", "imperial", "Fahrenheit", "mil/h", "hPa")
    )

    fun getUnits() = unitList

    fun saveAppUnit(unit: Unit) {
        unitPreferences.edit().putString(APP_UNIT, Gson().toJson(unit)).apply()
    }

    fun getAppUnit() = with(unitPreferences.getString(APP_UNIT, "")) {
        if (!this.isNullOrBlank()) Gson().fromJson(
            this,
            Unit::class.java
        ) else unitList.first().apply { chosen = true }
    }

}