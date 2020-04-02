package com.currentweather.dao

import androidx.room.*
import com.currentweather.domain.model.Coord
import com.currentweather.domain.model.forecast.ForecastThreeHoursModel

@Dao
abstract class ForecastThreeHoursDao {
    @Query("SELECT * FROM ForecastThreeHoursModel WHERE coord = :coord ORDER BY dt DESC LIMIT 1")
    abstract suspend fun getForecast(coord: Coord): ForecastThreeHoursModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertForecast(model: ForecastThreeHoursModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateForecast(model: ForecastThreeHoursModel): Int
}