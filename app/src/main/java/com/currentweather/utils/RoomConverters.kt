package com.currentweather.utils

import androidx.room.TypeConverter
import com.currentweather.domain.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

open class WeatherListTypeConverter<T>(val clazz: Class<T>){
    @TypeConverter
    fun fromList(list: List<T>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(data: String): List<T?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val type: Type = object :
            TypeToken<List<T>>() {}.type
        return Gson().fromJson(
            data,
            type
        )
    }
}

open class WeatherObjectTypeConverter<T>(val clazz: Class<T>){
    @TypeConverter
    fun fromObject(data: T): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun toObject(json: String): T? {
        val type: Type = object :
            TypeToken<T>() {}.type
        return Gson().fromJson(json, clazz)
    }

    private inline fun <reified R: T> f(json: String) : R?{
        return Gson().fromJson(json, R::class.java)
    }
}

class ListWeatherConverter : WeatherObjectTypeConverter<java.util.ArrayList<Weather>>(java.util.ArrayList<Weather>::class.java)
class CloudsConverter : WeatherObjectTypeConverter<Clouds>(Clouds::class.java)
class CoordConverter : WeatherObjectTypeConverter<Coord>(Coord::class.java)
class MainConverter : WeatherObjectTypeConverter<Main>(Main::class.java)
class SysConverter : WeatherObjectTypeConverter<Sys>(Sys::class.java)
class WindConverter : WeatherObjectTypeConverter<Wind>(Wind::class.java)
