package com.currentweather.ui.launch.loading

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.currentweather.CoroutineContextProvider
import com.currentweather.domain.LocationRepository
import com.currentweather.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoadingViewModel(private val coroutineContextProvider: CoroutineContextProvider, private val locationRepository: LocationRepository) : BaseViewModel() {

    val loading by lazy {
        MutableLiveData<LoadingState>()
    }

    override fun handleException(exception: Throwable) {
        loading.value = LoadingState.NOT_LOADED(exception)
    }

    fun loadLocation() {
        viewModelScope.launch(handler) {
            val location = withContext(coroutineContextProvider.io()) {
                locationRepository.getAppLocation()
            }
            loading.value = LoadingState.LOADED(location)
        }
    }

    sealed class LoadingState {
        data class LOADED(val data: Any) : LoadingState()
        data class NOT_LOADED(val throwable: Throwable) : LoadingState()
    }
}
