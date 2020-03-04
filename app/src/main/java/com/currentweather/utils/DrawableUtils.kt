package com.currentweather.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat


class DrawableUtils(private val context: Context) {
//    fun getBitmapFromVectorDrawable(drawableId: Int): Bitmap? {
//        var drawable =
//            ContextCompat.getDrawable(context, drawableId)
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            drawable = DrawableCompat.wrap(drawable!!).mutate()
//        }
//        val bitmap: Bitmap = Bitmap.createBitmap(
//            drawable!!.intrinsicWidth,
//            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
//        )
//        val canvas = Canvas(bitmap)
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
//        drawable.draw(canvas)
//        return bitmap
//    }

    fun getBitmapFromVectorDrawable(drawableId: Int) = with(ContextCompat.getDrawable(context, drawableId)){
        var drawable = this
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable!!).mutate()
        }
        return@with if(drawable != null) Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        ).apply {
            val canvas = Canvas(this)
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
            drawable.draw(canvas)
        } else null
    }
}