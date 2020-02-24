package com.currentweather.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    protected val handler = CoroutineExceptionHandler { _, exception ->
        handleException(exception)
    }

    abstract fun handleException(exception: Throwable)
}