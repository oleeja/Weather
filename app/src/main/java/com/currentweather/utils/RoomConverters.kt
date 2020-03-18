package com.currentweather.utils

import androidx.room.TypeConverter
import com.currentweather.domain.model.*
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import java.io.StringReader
import java.lang.reflect.Type
import java.util.*

open class WeatherListTypeConverter<T>(private val type: Type){
    @TypeConverter
    fun fromList(list: List<T>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toList(data: String?): List<T?>? {
        if (data == null) {
            return Collections.emptyList()
        }
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
        return Gson().fromJson(json, clazz)
    }
}

class ListWeatherConverter : WeatherListTypeConverter<Weather>(object : TypeToken<List<Weather>>(){}.type)
class CloudsConverter : WeatherObjectTypeConverter<Clouds>(Clouds::class.java)
class CoordConverter : WeatherObjectTypeConverter<Coord>(Coord::class.java)
class MainConverter : WeatherObjectTypeConverter<Main>(Main::class.java)
class SysConverter : WeatherObjectTypeConverter<Sys>(Sys::class.java)
class WindConverter : WeatherObjectTypeConverter<Wind>(Wind::class.java)
