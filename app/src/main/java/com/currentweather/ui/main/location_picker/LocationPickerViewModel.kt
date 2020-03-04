package com.currentweather.ui.main.location_picker

import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.currentweather.CoroutineContextProvider
import com.currentweather.domain.LocationRepository
import com.currentweather.ui.base.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationPickerViewModel(private val coroutineContextProvider: CoroutineContextProvider,
                              private val locationRepository: LocationRepository
) : BaseViewModel(), LifecycleObserver {

    private var location: LatLng? = null

    val currentWeatherData by lazy{
        MutableLiveData<Location>()
    }

    fun getLocation() = location

    fun setLocation(latLng: LatLng) {
        location = latLng
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadAppLocation() {
        viewModelScope.launch(handler) {
            val location = withContext(coroutineContextProvider.io()) {
                locationRepository.getAppLocation()
            }
            currentWeatherData.value = location
        }
    }

    override fun handleException(exception: Throwable) {
        //TODO: handle exeption
       Log.d("", "")
    }

    fun changeLocation(latLng: LatLng?) {
        latLng?.let {
            viewModelScope.launch(handler) {
                withContext(coroutineContextProvider.io()) {
                    locationRepository.saveAppLocation(it)
                }
            }
        }
    }
}
