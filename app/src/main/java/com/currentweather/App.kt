package com.currentweather

import android.app.Application
import com.currentweather.ui.di.*
import com.currentweather.ui.launch.loading.loadingRepositoryModule
import com.currentweather.ui.launch.loading.loadingViewModule
import com.currentweather.ui.main.current_weather.weatherRepositoryModule
import com.currentweather.ui.main.current_weather.weatherViewModule
import com.currentweather.ui.main.location_picker.locationPickerRepositoryModule
import com.currentweather.ui.main.location_picker.locationPickerViewModule
import com.currentweather.ui.main.notification.notificationRepositoryModule
import com.currentweather.ui.main.notification.notificationSettingsViewModule
import com.currentweather.ui.main.settings.settingsViewModule
import com.currentweather.ui.main.units.unitsRepositoryModule
import com.currentweather.ui.main.units.unitsViewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(commonModule + dataSourceModule + retrofitNetworkModule + utilsModule + onGoingNotificationModule)
            modules(
                loadingRepositoryModule + weatherViewModule + weatherRepositoryModule
                        + settingsViewModule + unitsViewModule + unitsRepositoryModule
                        + locationPickerViewModule + loadingViewModule + locationPickerRepositoryModule
                        + notificationSettingsViewModule + notificationRepositoryModule
            )
        }
    }
}