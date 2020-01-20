package com.currentweather.data

import com.currentweather.api.provideWeatherService
import com.currentweather.data.model.WeatherModel

class Repository {

    private val dataSource = provideWeatherService()

    suspend fun getWeaterData(cityId: Int) : WeatherModel = dataSource.getWeather(cityId).body() ?: throw Exception()
}