package com.currentweather.api

import com.currentweather.data.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object {
        const val ENDPOINT = "http://api.openweathermap.org/data/2.5/"
    }

    @GET("weather")
    suspend fun getWeather(@Query("id") page: Int): Response<WeatherModel>

}