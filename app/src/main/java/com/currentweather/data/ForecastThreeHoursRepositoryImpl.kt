package com.currentweather.data

import android.location.Location
import com.currentweather.api.WeatherService
import com.currentweather.domain.ForecastRepository
import com.currentweather.domain.model.forecast.ForecastThreeHoursModel

class ForecastThreeHoursRepositoryImpl(private val dataSource : WeatherService)  : ForecastRepository {
    override suspend fun getForecast(location: Location): ForecastThreeHoursModel {
        val data = dataSource.getFiveDaysForecast(location.latitude, location.longitude)
        if (data.isSuccessful && data.body() != null) {
            return data.body() as ForecastThreeHoursModel
        }
        throw Exception()
    }
}