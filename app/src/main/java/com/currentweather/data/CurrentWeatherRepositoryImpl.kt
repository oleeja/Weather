package com.currentweather.data

import android.location.Location
import com.currentweather.api.WeatherService
import com.currentweather.domain.CurrentWeatherRepository
import com.currentweather.domain.model.WeatherModel

class CurrentWeatherRepositoryImpl(private val dataSource : WeatherService) : CurrentWeatherRepository {

    override suspend fun getWeatherData(location: Location): WeatherModel {
        val data = dataSource.getWeather(location.latitude, location.longitude)
        if (data.isSuccessful && data.body() != null) {
            return data.body() as WeatherModel
        }
        throw Exception()
    }
}