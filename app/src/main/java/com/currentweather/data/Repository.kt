package com.currentweather.data

import com.currentweather.api.provideWeatherService

class Repository {

    private val dataSource = provideWeatherService()

    suspend fun getWeaterData(cityId: Int) = dataSource.getWeather(cityId)
}