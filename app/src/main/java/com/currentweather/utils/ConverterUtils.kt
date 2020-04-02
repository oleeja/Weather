package com.currentweather.utils

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.round2Decimal() =
    DecimalFormat("#.##").apply { roundingMode = RoundingMode.FLOOR }.format(this).toDouble()