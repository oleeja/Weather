package com.currentweather.ui.main.current_weather

import android.content.res.Configuration
import android.location.Location
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.currentweather.CoroutineContextProvider
import com.currentweather.domain.CurrentWeatherRepository
import com.currentweather.domain.ForecastRepository
import com.currentweather.domain.LocationRepository
import com.currentweather.domain.UnitsRepository
import com.currentweather.domain.model.Unit
import com.currentweather.domain.model.WeatherModel
import com.currentweather.domain.model.forecast.ForecastThreeHoursModel
import com.currentweather.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class WeatherViewModel(
    private val currentWeatherRepository: CurrentWeatherRepository,
    private val forecastRepository: ForecastRepository,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val locationRepository: LocationRepository,
    private val unitsRepository: UnitsRepository
) : BaseViewModel(), LifecycleObserver {

    var isLoading = ObservableBoolean()

    var orientation: Int = Configuration.ORIENTATION_PORTRAIT

    override fun handleException(exception: Throwable) {
        data.postValue(
            ViewState.Error(
                exception
            )
        )
    }

    private val data by lazy {
        MutableLiveData<ViewState>()
    }

    fun getViewModelLiveData(): LiveData<ViewState> = data

    val currentWeatherData by lazy {
        MutableLiveData<WeatherModel>()
    }

    val forecastWeatherData by lazy {
        MutableLiveData<ForecastThreeHoursModel>()
    }

    val currentUnits by lazy {
        MutableLiveData<Unit>()
    }

    val location by lazy {
        MutableLiveData<Location>()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getData() {
        viewModelScope.launch(handler + coroutineContextProvider.io()) {
            currentUnits.postValue(unitsRepository.getAppUnits())
        }
        viewModelScope.launch(handler + coroutineContextProvider.io()) {
            val currentLocation = locationRepository.getLocation()
            location.postValue(currentLocation)
            currentWeatherData.postValue(currentWeatherRepository.getWeatherData(currentLocation))
            viewModelScope.launch(handler) {
                forecastWeatherData.postValue(forecastRepository.getForecast(currentLocation))
            }
        }
    }

    fun onRefresh() {
        isLoading.set(true)
        getData()
        isLoading.set(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopData() {
        // TODO: add unsubscribeLocation
    }

    sealed class ViewState {
        data class Success(val data: Any) : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
    }
}