package com.currentweather.dao

import androidx.room.*
import com.currentweather.domain.model.forecast.ForecastThreeHoursModel

@Dao
abstract class ForecastThreeHoursDao {
    @Query("SELECT * FROM ForecastThreeHoursModel")
    abstract suspend fun getForecast(): List<ForecastThreeHoursModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertForecast(model: ForecastThreeHoursModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateForecast(model: ForecastThreeHoursModel): Int
}