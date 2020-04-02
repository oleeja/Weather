package com.currentweather.dao

import androidx.room.*
import com.currentweather.domain.model.Coord
import com.currentweather.domain.model.WeatherModel

@Dao
abstract class CurrentWeatherDao {
    @Query("SELECT * FROM WeatherModel WHERE coord = :coord ORDER BY dt DESC LIMIT 1")
    abstract suspend fun getLastCurrentWeather(coord: Coord): WeatherModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertCurrentWeather(model: WeatherModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateCurrentWeather(model: WeatherModel): Int

}