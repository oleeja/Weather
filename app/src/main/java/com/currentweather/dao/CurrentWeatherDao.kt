package com.currentweather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currentweather.domain.model.WeatherModel

@Dao
abstract class CurrentWeatherDao {
    @Query("SELECT * FROM WeatherModel ORDER BY dt DESC LIMIT 1")
    abstract suspend fun getLastCurrentWeather(): WeatherModel

    //In case of migration to Flow
//    @ExperimentalCoroutinesApi
//    suspend fun getWeatherDistinctUntilChanged() =
//        getLastCurrentWeather().distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateCurrentWeather(model: WeatherModel): Long

}