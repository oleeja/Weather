package com.currentweather.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.currentweather.domain.model.WeatherModel
import com.currentweather.domain.model.forecast.City
import com.currentweather.domain.model.forecast.ForecastThreeHoursModel
import com.currentweather.domain.model.forecast.Rain
import com.currentweather.utils.*

/**
 * The Room database that contains the Users table
 */
@Database(entities = [WeatherModel::class, ForecastThreeHoursModel::class, City::class], version = 1, exportSchema = false)
@TypeConverters(ListWeatherConverter::class, CloudsConverter::class,
    CoordConverter::class, MainConverter::class, SysConverter::class,
    WindConverter::class, ListForecastThreeHoursConverter::class,
    CityConverter::class, RainConverter::class, SysForecastConverter::class)
abstract class WeatherDatabase : RoomDatabase(){

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun forecastThreeHoursDao(): ForecastThreeHoursDao

    companion object {

        @Volatile private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java, "BestWeather.db")
                .build()
    }
}
