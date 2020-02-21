package com.currentweather.utils

import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.currentweather.R
import com.squareup.picasso.Picasso
import java.util.*


@BindingConversion
fun convertNumberToString(i: Number?) = i?.toString() ?: ""

@BindingAdapter("app:backroundColor")
fun convertTimeStampToColor(view: View, timestamp: Long?) {
    view.setBackgroundColor(ContextCompat.getColor(view.context, getResourceBackgroundMain(timestamp)))
}

@BindingAdapter("app:backroundColorSecondary")
fun convertTimeStampToColorSecondary(view: View, timestamp: Long?) {
    view.setBackgroundColor(ContextCompat.getColor(view.context, getResourceBackgroundSecondary(timestamp)))
}

@ColorRes
fun getResourceBackgroundMain(timestamp: Long?) : Int{
    return when (Calendar.getInstance().apply { timeInMillis = (timestamp ?:0)*1000 }.get(Calendar.HOUR_OF_DAY)) {
        in 0..6 -> R.color.nightPrimary
        in 7..11 -> R.color.morningPrimary
        in 12..18 -> R.color.afternoonPrimary
        in 18..22 -> R.color.eveningPrimary
        else -> R.color.nightPrimary
    }
}

@ColorRes
fun getResourceBackgroundSecondary(timestamp: Long?) : Int{
    return when (Calendar.getInstance().apply { timeInMillis = (timestamp ?:0)*1000 }.get(Calendar.HOUR_OF_DAY)) {
        in 0..6 -> R.color.nightPrimaryDark
        in 7..11 -> R.color.morningPrimaryDark
        in 12..18 -> R.color.afternoonPrimaryDark
        in 18..22 -> R.color.eveningPrimaryDark
        else -> R.color.nightPrimaryDark
    }
}

@BindingAdapter("app:icon")
fun setIcon(view: ImageView, iconCode: String?) {
    Picasso.with(view.context).load("http://openweathermap.org/img/wn/$iconCode@2x.png").into(view)
}

@BindingAdapter("app:dateTime")
fun convertTimeStampToText(view: TextView, timestamp: Long?) {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = (timestamp ?:0) * 1000L
    val date = DateFormat.format("dd/MM EEE", calendar).toString()
    view.text = date
}
