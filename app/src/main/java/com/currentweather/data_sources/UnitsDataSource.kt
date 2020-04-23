package com.currentweather.data_sources

import android.content.Context
import android.content.SharedPreferences
import com.currentweather.R
import com.currentweather.domain.model.Unit
import com.google.gson.Gson


class UnitsDataSource(context: Context) {

    private val unitPreferences: SharedPreferences =
        context.getSharedPreferences("UNITS_PREFERENCES", Context.MODE_PRIVATE)

    private val APP_UNIT = "APP_UNIT"

    private val unitList = arrayListOf(
        Unit(
            "Standard",
            "",
            context.getString(R.string.unit_kelvin),
            context.getString(R.string.unit_wind_met),
            context.getString(R.string.unit_pressure_pa)
        ),
        Unit(
            "Metric",
            "metric",
            context.getString(R.string.unit_celsius),
            context.getString(R.string.unit_wind_met),
            context.getString(R.string.unit_pressure_pa)
        ),
        Unit(
            "Imperial",
            "imperial",
            context.getString(R.string.unit_fahrenheit),
            context.getString(R.string.unit_wind_mil),
            context.getString(R.string.unit_pressure_pa)
        )
    )

    fun getUnits() = unitList

    fun saveAppUnit(unit: Unit) {
        unitPreferences.edit().putString(APP_UNIT, Gson().toJson(unit)).apply()
    }

    fun getAppUnit(): Unit = with(unitPreferences.getString(APP_UNIT, "")) {
        if (!this.isNullOrBlank()) Gson().fromJson(
            this,
            Unit::class.java
        ) else unitList.first().apply { chosen = true }
    }

}