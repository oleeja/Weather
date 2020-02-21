package com.currentweather.domain

import android.location.Location
import com.currentweather.domain.model.WeatherModel

interface CurrentWeatherRepository {
    suspend fun getWeatherData(location: Location): WeatherModel
}