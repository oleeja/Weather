package com.currentweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currentweather.CoroutineContextProvider
import com.currentweather.data.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(val repository: Repository, val coroutineContextProvider: CoroutineContextProvider) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exeption ->
        weather.value = ViewState.Error(exeption)
    }

    private val cityId = 2172797

    private val weather by lazy {
        MutableLiveData<ViewState>()
    }

    fun getViewModelLiveData() : LiveData<ViewState> = weather

    fun getData()  {
        viewModelScope.launch(handler) {
            val data = withContext(coroutineContextProvider.io()) {
                repository.getWeaterData(cityId)
            }
            weather.value = ViewState.Success(data)
        }
    }

    sealed class ViewState {
        data class Success(val data: Any) : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
    }
}