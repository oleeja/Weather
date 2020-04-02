package com.currentweather.dao

import androidx.room.*
import com.currentweather.domain.model.Coord
import com.currentweather.domain.model.forecast.City
import com.currentweather.domain.model.forecast.ForecastThreeHoursModel

@Dao
abstract class ForecastThreeHoursDao {
    @Query("SELECT * FROM ForecastThreeHoursModel WHERE city = :city")
    abstract suspend fun getForecast(city: City): ForecastThreeHoursModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertForecast(model: ForecastThreeHoursModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateForecast(model: ForecastThreeHoursModel)
}