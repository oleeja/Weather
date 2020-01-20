package com.currentweather.data

import com.currentweather.api.provideWeatherService
import com.currentweather.data.model.WeatherModel

class Repository {

    private val dataSource = provideWeatherService()

    suspend fun getWeaterData(cityId: Int): WeatherModel {
        val data = dataSource.getWeather(cityId, "98fa0b9cfe122d8fb6a87e539fcf4876")
        if (data.isSuccessful && data.body() != null) {
            return data.body() as WeatherModel
        }
        throw Exception()
    }
}