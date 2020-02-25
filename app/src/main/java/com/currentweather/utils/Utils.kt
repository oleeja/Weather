package com.currentweather.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.currentweather.R
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.squareup.picasso.Picasso
import java.util.*


@BindingConversion
fun convertNumberToString(i: Number?) = i?.toString() ?: "--"

@BindingAdapter("app:backroundColor")
fun convertTimeStampToColor(view: View, timestamp: Long?) {
    view.setBackgroundColor(
        ContextCompat.getColor(
            view.context,
            getResourceBackgroundMain(timestamp)
        )
    )
}

@BindingAdapter("app:backroundColorSecondary")
fun convertTimeStampToColorSecondary(view: View, timestamp: Long?) {
    view.setBackgroundColor(
        ContextCompat.getColor(
            view.context,
            getResourceBackgroundSecondary(timestamp)
        )
    )
}

@ColorRes
fun getResourceBackgroundMain(timestamp: Long?): Int {
    return when (Calendar.getInstance().apply { timeInMillis = (timestamp ?: 0) * 1000 }.get(
        Calendar.HOUR_OF_DAY
    )) {
        in 0..6 -> R.color.nightPrimary
        in 7..11 -> R.color.morningPrimary
        in 12..18 -> R.color.afternoonPrimary
        in 18..22 -> R.color.eveningPrimary
        else -> R.color.nightPrimary
    }
}

@ColorRes
fun getResourceBackgroundSecondary(timestamp: Long?): Int {
    return when (Calendar.getInstance().apply { timeInMillis = (timestamp ?: 0) * 1000 }.get(
        Calendar.HOUR_OF_DAY
    )) {
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

@BindingAdapter("app:mappedIcon")
fun setMappedIcon(view: ImageView, iconCode: String?) {
    iconCode?.let { view.setImageResource(getLocalIconFromIconCode(iconCode)) }
}

@BindingAdapter("app:dateTime")
fun convertTimeStampToText(view: TextView, timestamp: Long?) {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = (timestamp ?: 0) * 1000L
    val date = DateFormat.format("dd/MM HH:mm", calendar).toString()
    view.text = date
}

@BindingAdapter(value = ["android:drawableStart", "app:degree"], requireAll = false)
fun rotateArrow(view: AppCompatTextView, iconRes: Drawable?, degree: Double?) {
    iconRes?.let {
        if (degree == null || degree == 0.0) view.setCompoundDrawablesWithIntrinsicBounds(
            iconRes,
            null,
            null,
            null
        )
        else {
            view.setCompoundDrawablesWithIntrinsicBounds(
                BitmapDrawable(view.context.resources,
                    with(
                        getBitmapFromVectorDrawable(
                            iconRes
                        )
                    ) {
                        val matrix = android.graphics.Matrix()
                        matrix.postRotate(degree.toFloat())
                        this?.let {
                            Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, true)
                        }

                    }),
                null, null, null
            )
        }
    }

}

private fun getBitmapFromVectorDrawable(drawableRes: Drawable): Bitmap? {
    var drawable = drawableRes
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        drawable = DrawableCompat.wrap(drawable).mutate()
    }
    return Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    ).apply {
        val canvas = Canvas(this)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
    }
}

@BindingAdapter("app:temperatureText")
fun convertTimeStampToText(view: TextView, temp: Number?) {
    val temp = "${temp?.toString() ?: "--"}°"
    view.text = temp

    //convertClassKotlinToJava(com.currentweather.domain.model.Unit::class)
}


@BindingAdapter(value = ["app:items", "app:itemLayout", "app:onClick"], requireAll = true)
fun <T : Any> setRecyclerAdapter(view: RecyclerView, items: List<Any>?, itemLayoutRes: Int, click: OnPositionClickListener) {
    if (!items.isNullOrEmpty()) {
        val typeClass = items[0].javaClass
        val typeItem = Type<ViewDataBinding>(itemLayoutRes)
            .onClick { click.onClick(it.adapterPosition)}

        LastAdapter(items, BR.model)
            .map(typeClass, typeItem)
            .into(view)
    }
}


interface OnPositionClickListener{
    fun onClick(position: Int)
}