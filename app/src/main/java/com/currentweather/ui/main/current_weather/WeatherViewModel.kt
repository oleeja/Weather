package com.currentweather.ui.main.current_weather

import android.content.res.Configuration
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
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WeatherViewModel(
    val currentWeatherRepository: CurrentWeatherRepository,
    val forecastRepository: ForecastRepository,
    val coroutineContextProvider: CoroutineContextProvider,
    val locationRepository: LocationRepository,
    val unitsRepository: UnitsRepository
) : BaseViewModel(), LifecycleObserver {

    var isLoading = ObservableBoolean()

    var orientation : Int = Configuration.ORIENTATION_PORTRAIT

    override fun handleException(exception: Throwable) {
        data.value =
            ViewState.Error(
                exception
            )
    }

    private val data by lazy {
        MutableLiveData<ViewState>()
    }

    fun getViewModelLiveData(): LiveData<ViewState> = data

    val currentWeatherData by lazy{
        MutableLiveData<WeatherModel>()
    }

    val forecastWeatherData by lazy{
        MutableLiveData<ForecastThreeHoursModel>()
    }

    val currentUnits by lazy{
        MutableLiveData<Unit>()
    }

    fun getData() {
        viewModelScope.launch(handler) {
            val location = withContext(coroutineContextProvider.io()) {
                locationRepository.getAppLocation()
            }
            currentWeatherData.value = withContext(coroutineContextProvider.io()) {
                currentWeatherRepository.getWeatherData(location)
            }
            forecastWeatherData.value = withContext(coroutineContextProvider.io()) {
                forecastRepository.getForecast(location)
            }
            currentUnits.value = withContext(coroutineContextProvider.io()) {
                unitsRepository.getAppUnits()
            }
            data.value =
                ViewState.Success(
                    location
                )
            currentWeatherData.value?.let { data.value = ViewState.Success(it) }
        }
    }

    fun onRefresh(){
        isLoading.set(true)
        getData()
        isLoading.set(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopData() {
        // TODO: add unsubscribeLocation
    }

    fun changeLocation(latLng: LatLng?) {
        latLng?.let {
            viewModelScope.launch(handler) {
                withContext(coroutineContextProvider.io()) {
                    locationRepository.saveAppLocation(it)
                    onRefresh()
                }
            }
        }
    }

    sealed class ViewState {
        data class Success(val data: Any) : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
    }
}