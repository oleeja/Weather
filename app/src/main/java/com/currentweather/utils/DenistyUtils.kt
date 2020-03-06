package com.currentweather.utils

import android.content.Context

/**
 * dip to px
 */
fun dpToPx(context: Context, dpValue: Float)
        = (dpValue * context.resources.displayMetrics.density + 0.5f).toInt()

/**
 * px to dp
 */
fun pxToDp(context: Context, pxValue: Float)
        = (pxValue / context.resources.displayMetrics.density + 0.5f).toInt()