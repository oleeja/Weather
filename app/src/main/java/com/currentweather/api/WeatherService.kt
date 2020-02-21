package com.currentweather.api

import com.currentweather.domain.model.WeatherModel
import com.currentweather.domain.model.forecast.ForecastThreeHoursModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object {
        const val ENDPOINT = "http://api.openweathermap.org/data/2.5/"
    }

    @GET("weather")
    suspend fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Response<WeatherModel>

    @GET("forecast")
    suspend fun getFiveDaysForecast(@Query("lat") lat: Double, @Query("lon") lon: Double): Response<ForecastThreeHoursModel>

}