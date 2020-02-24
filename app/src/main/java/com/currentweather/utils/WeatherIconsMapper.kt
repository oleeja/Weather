package com.currentweather.utils

import androidx.annotation.DrawableRes
import com.currentweather.R

@DrawableRes
fun getLocalIconFromIconCode(iconCode: String): Int{
    return when(iconCode){
        "01d" -> R.drawable.ic_mostly_sunny
        "01n" -> R.drawable.ic_clear_night
        "02d" -> R.drawable.ic_party_cloudy
        "02n" -> R.drawable.ic_party_cloudy_night
        "03d" -> R.drawable.ic_mostly_cloudy
        "03n" -> R.drawable.ic_mostly_cloudy_night
        "04d" -> R.drawable.ic_mostly_cloudy
        "04n" -> R.drawable.ic_mostly_cloudy_night
        "09d" -> R.drawable.ic_heavy_rain
        "09n" -> R.drawable.ic_heavy_rain_night
        "10d" -> R.drawable.ic_rain
        "10n" -> R.drawable.ic_rain_nght
        "11d" -> R.drawable.ic_severe_thunderstorm
        "11n" -> R.drawable.ic_severe_thunderstorm_night
        "13d" -> R.drawable.ic_snow
        "13n" -> R.drawable.ic_snow_night
        "50d" -> R.drawable.ic_fog
        "50n" -> R.drawable.ic_fog_night
        else -> R.drawable.ic_mostly_sunny
    }
}