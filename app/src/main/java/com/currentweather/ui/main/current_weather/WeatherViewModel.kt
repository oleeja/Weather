package com.currentweather.ui.main.current_weather

import androidx.lifecycle.*
import com.currentweather.CoroutineContextProvider
import com.currentweather.domain.CurrentWeatherRepository
import com.currentweather.domain.ForecastRepository
import com.currentweather.domain.LocationRepository
import com.currentweather.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    val currentWeatherRepository: CurrentWeatherRepository,
    val forecastRepository: ForecastRepository,
    val coroutineContextProvider: CoroutineContextProvider,
    val locationRepository: LocationRepository
) : BaseViewModel(), LifecycleObserver {

    private val handler = CoroutineExceptionHandler { _, exeption ->
        weather.value =
            ViewState.Error(
                exeption
            )
    }

    private val weather by lazy {
        MutableLiveData<ViewState>()
    }

    fun getViewModelLiveData(): LiveData<ViewState> = weather

    fun getData() {
        viewModelScope.launch(handler) {
            val location = withContext(coroutineContextProvider.io()) {
                locationRepository.getAppLocation()
            }
            val currentWeatherData = withContext(coroutineContextProvider.io()) {
                currentWeatherRepository.getWeatherData(location)
            }
            val forecastWeatherData = withContext(coroutineContextProvider.io()) {
                forecastRepository.getForecast(location)
            }
            weather.value =
                ViewState.Success(
                    location
                )
            weather.value =
                ViewState.Success(
                    currentWeatherData
                )
            weather.value =
                ViewState.Success(
                    forecastWeatherData
                )
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopData() {

    }

    sealed class ViewState {
        data class Success(val data: Any) : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
    }
}