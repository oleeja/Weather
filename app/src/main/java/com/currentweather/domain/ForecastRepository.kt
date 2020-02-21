package com.currentweather.domain

import android.location.Location
import com.currentweather.domain.model.forecast.ForecastThreeHoursModel

interface ForecastRepository {
    suspend fun getForecast(location: Location) : ForecastThreeHoursModel
}