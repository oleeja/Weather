package com.currentweather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currentweather.data.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class WeatherViewModel : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exeption ->
        ViewState.Error(exeption)

    }

    private val repository = Repository()
    private val cityId = 2172797

    val weather by lazy {
        getData()
    }

    sealed class ViewState {
        object Loading: ViewState()
        data class Success(val data: Any) : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
    }

    fun getData() = MutableLiveData<ViewState>().apply {
        value = ViewState.Loading
        viewModelScope.launch(handler) {
            val data = withContext(Dispatchers.IO) {
                repository.getWeaterData(cityId)
            }
            value = ViewState.Success(data)
        }
    }
}