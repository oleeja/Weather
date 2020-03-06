package com.currentweather.ui.main.notification

import android.location.Location
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.currentweather.BR
import com.currentweather.CoroutineContextProvider
import com.currentweather.domain.LocationRepository
import com.currentweather.domain.OnGoingRefreshRepository
import com.currentweather.domain.model.notification.NotificationSettings
import com.currentweather.ui.base.BaseObservableViewModel
import com.currentweather.ui.notification_ui.OnGoingNotificationHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationSettingsViewModel(
    private val locationRepository: LocationRepository,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val notificationSettings: NotificationSettings,
    private val onGoingRefreshRepository: OnGoingRefreshRepository,
    private val notificationUiHandler: OnGoingNotificationHandler
) : BaseObservableViewModel(), LifecycleObserver {

    override fun handleException(exception: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val settings by lazy {
        MutableLiveData(notificationSettings)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getData() {
        viewModelScope.launch(handler) {
            val location = withContext(coroutineContextProvider.io()) {
                locationRepository.getLocation()
            }
            setNotificationLocation(location)
        }
    }

    @Bindable
    fun getViewModelLiveData(): LiveData<NotificationSettings> = settings

    @Bindable
    fun getNotificationEnabled() = notificationSettings.isStatusBarNotificationEnable

    fun setNotificationEnabled(enabled: Boolean){
        this.settings.value?.isStatusBarNotificationEnable = enabled
        notifyPropertyChanged(BR.notificationEnabled)
    }

    @Bindable
    fun getNotificationLocation() = notificationSettings.notificationLocation

    fun setNotificationLocation(location: Location){
        this.settings.value?.notificationLocation = location
        notifyPropertyChanged(BR.notificationLocation)
    }

    fun openLocation(){

    }

    fun saveSettings() {
        viewModelScope.launch(handler) {
            settings.value?.let {
                onGoingRefreshRepository.enableNotification()
                notificationUiHandler.run {
                    if (it.isStatusBarNotificationEnable)
                        showNotification()
                    else
                        dismissNotification()
                }
            }
        }

    }
}
