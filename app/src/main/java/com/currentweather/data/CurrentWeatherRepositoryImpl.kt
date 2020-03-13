package com.currentweather.data

import android.location.Location
import com.currentweather.api.WeatherService
import com.currentweather.dao.CurrentWeatherDao
import com.currentweather.domain.CurrentWeatherRepository
import com.currentweather.domain.model.WeatherModel

class CurrentWeatherRepositoryImpl(private val dataSource : WeatherService, private val currentWeatherDao: CurrentWeatherDao) : CurrentWeatherRepository {

    override suspend fun getWeatherData(location: Location): WeatherModel {
        val data = try {
            dataSource.getWeather(location.latitude, location.longitude)
        } catch (e: Exception){
            null
        }
        return if (data != null && data.isSuccessful && data.body() != null) {
            val result = currentWeatherDao.updateCurrentWeather(data.body() as WeatherModel)
            data.body() as WeatherModel
        } else{
            currentWeatherDao.getLastCurrentWeather()
        }
    }
}