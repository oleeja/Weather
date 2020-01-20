package com.currentweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currentweather.DispatcherProvider
import com.currentweather.data.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel : ViewModel() {

    val repository = Repository()
    private val cityId = 2172797

    private val weather by lazy {
        MutableLiveData<ViewState>()
    }

    fun getViewModelLiveData() : LiveData<ViewState> = weather

    fun getData(dispatcherProvider: DispatcherProvider){
        viewModelScope.launch(dispatcherProvider.io()) {
            val data = repository.getWeaterData(cityId)
            withContext(dispatcherProvider.io()) {
                weather.value = ViewState.Success(data)
            }
        }
    }

    sealed class ViewState {
        data class Success(val data: Any) : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
    }
}