package com.currentweather.data

import android.location.Location
import com.currentweather.api.WeatherService
import com.currentweather.dao.ForecastThreeHoursDao
import com.currentweather.domain.ForecastRepository
import com.currentweather.domain.model.Coord
import com.currentweather.domain.model.forecast.ForecastThreeHoursModel
import com.currentweather.utils.round2Decimal
import java.util.concurrent.TimeUnit

class ForecastThreeHoursRepositoryImpl(private val dataSource : WeatherService, private val forecastThreeHoursDao: ForecastThreeHoursDao)  : ForecastRepository {
    override suspend fun getForecast(location: Location): ForecastThreeHoursModel {
        val cachedData = try {
            //TODO: After changing models make normal request to db
            forecastThreeHoursDao.getForecast(
                Coord(
                    location.latitude.round2Decimal(),
                    location.longitude.round2Decimal()
                )
            )
        } catch (e: Exception) {
            null
        }
        if (cachedData!= null && cachedData.dt + TimeUnit.MINUTES.toMillis(5) >= System.currentTimeMillis() ) {
            return cachedData
        }
        val data = try {
            dataSource.getFiveDaysForecast(location.latitude, location.longitude)
        } catch (e: Exception) {
            null
        }
        return if (data != null && data.isSuccessful && data.body() != null) {
            forecastThreeHoursDao.run {
                val forecast = data.body() as ForecastThreeHoursModel
                forecast.coord =
                    Coord(location.latitude.round2Decimal(), location.longitude.round2Decimal())
                if (cachedData == null) {
                    insertForecast(forecast)
                } else {
                    updateForecast(forecast)
                }
            }
            data.body() as ForecastThreeHoursModel
        } else {
            cachedData ?: throw Exception("Can't receive weather data")
        }
    }
}