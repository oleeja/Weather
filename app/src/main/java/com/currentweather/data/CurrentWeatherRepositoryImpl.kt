package com.currentweather.data

import android.location.Location
import com.currentweather.api.WeatherService
import com.currentweather.dao.CurrentWeatherDao
import com.currentweather.domain.CurrentWeatherRepository
import com.currentweather.domain.model.WeatherModel
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CurrentWeatherRepositoryImpl(
    private val dataSource: WeatherService,
    private val currentWeatherDao: CurrentWeatherDao
) : CurrentWeatherRepository {

    override suspend fun getWeatherData(location: Location): WeatherModel {
        val cachedData = try {
            currentWeatherDao.getLastCurrentWeather()
        } catch (e: Exception) {
            null
        }
        if (cachedData!= null && cachedData.dt * 1000L <= System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)) {
            return cachedData
        }
        val data = try {
            dataSource.getWeather(location.latitude, location.longitude)
        } catch (e: Exception) {
            null
        }
        return if (data != null && data.isSuccessful && data.body() != null) {
            currentWeatherDao.run {
                if (cachedData == null) {
                    insertCurrentWeather(data.body() as WeatherModel)
                } else {
                    updateCurrentWeather(data.body() as WeatherModel)
                }

            }
            data.body() as WeatherModel
        } else {
            cachedData ?: throw Exception("Can't receive weather data")
        }
    }
}