package com.currentweather.utils

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import com.currentweather.R


class StatusBarIconUtils (val context: Context) {

    fun getTemperatureBitmap(temperature: Int): Bitmap {
        val tempString = "$temperature°"
        val paint = Paint(ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textAlign = Paint.Align.LEFT
            textSize = context.resources.getDimension(R.dimen.sb_temp_icon_size)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val baseline = -paint.ascent()
        val width = (paint.measureText(tempString) + 0.5f).toInt()
        val height = (paint.textSize + 0.5f).toInt()
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            Canvas(this).drawText(tempString, 0f, baseline, paint)
        }
    }

    //Left for case if condition icon in status bar will be needed
//    @RequiresApi(Build.VERSION_CODES.M)
//    fun getStatusBarIcon(temperature: Int? = null, conditionIconCode: Int? = null) = Icon.createWithBitmap(when {
//        conditionIconCode == null && temperature != null -> getTemperatureBitmap(temperature)
//        temperature == null && conditionIconCode != null -> getConditionsBitmap(conditionIconCode)
//        temperature != null && conditionIconCode != null -> getTempWithConditionsBitmap(temperature, conditionIconCode)
//        else -> throw IllegalArgumentException("At least one argument must be not null")
//    })

//    private fun getConditionsBitmap(conditionIconCode: Int) =
//            with(AppCompatResources.getDrawable(context, WxIconItem(conditionIconCode).iconId)) {
//                this ?: throw IllegalArgumentException("can't convert icon resource to drawable")
//                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
//                    val canvas = Canvas(this)
//                    setBounds(0, 0, canvas.width, canvas.height)
//                    draw(canvas)
//                }
//            }

//    private fun getTempWithConditionsBitmap(temperature: Int, conditionIconCode: Int): Bitmap {
//        val temperatureBitmap = getTemperatureBitmap(temperature)
//        val conditionBitmap = getConditionsBitmap(conditionIconCode)
//
//        val height = if (temperatureBitmap.height < conditionBitmap.height) temperatureBitmap.height else conditionBitmap.height
//
//        val scaledConditionWidth = height * conditionBitmap.width / conditionBitmap.height
//        val scaledTemperatureWidth = height * temperatureBitmap.width / temperatureBitmap.height
//
//        val width = if (scaledTemperatureWidth > scaledConditionWidth)
//            scaledTemperatureWidth else scaledConditionWidth
//
//        val conditionPlacePercent = 0.35
//        val temperaturePlacePercent = 0.65
//
//        val conditionWidth = scaledConditionWidth.times(conditionPlacePercent).toInt()
//        val temperatureWidth = scaledTemperatureWidth.times(temperaturePlacePercent).toInt()
//
//        val leftTemperaturePadding = if (temperatureWidth > conditionWidth) 0 else (conditionWidth - temperatureWidth) / 2
//        val leftConditionPadding = if (conditionWidth > temperatureWidth) 0 else (temperatureWidth - conditionWidth) / 2
//
//        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
//
//            Canvas(this).drawBitmap(
//                    Bitmap.createScaledBitmap(conditionBitmap,
//                            conditionWidth,
//                            height.times(conditionPlacePercent).toInt(),
//                            false),
//                    leftConditionPadding.toFloat(), 0f, Paint().apply { color = Color.WHITE })
//
//            Canvas(this).drawBitmap(
//                    Bitmap.createScaledBitmap(temperatureBitmap,
//                            temperatureWidth,
//                            height.times(temperaturePlacePercent).toInt(),
//                            false),
//                    leftTemperaturePadding.toFloat(), height.times(conditionPlacePercent).toFloat(), Paint().apply { color = Color.WHITE })
//        }
//    }
}