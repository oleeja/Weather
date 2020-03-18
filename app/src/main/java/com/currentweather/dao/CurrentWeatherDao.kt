package com.currentweather.dao

import androidx.room.*
import com.currentweather.domain.model.WeatherModel

@Dao
abstract class CurrentWeatherDao {
    @Query("SELECT * FROM WeatherModel ORDER BY dt DESC LIMIT 1")
    abstract suspend fun getLastCurrentWeather(): WeatherModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertCurrentWeather(model: WeatherModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateCurrentWeather(model: WeatherModel)

}