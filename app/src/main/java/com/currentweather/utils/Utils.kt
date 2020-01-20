package com.currentweather.utils

import androidx.databinding.BindingConversion


@BindingConversion
fun convertNumberToString(i: Number?) = i?.toString() ?: ""