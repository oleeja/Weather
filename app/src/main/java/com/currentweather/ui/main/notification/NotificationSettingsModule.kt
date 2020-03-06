package com.currentweather.ui.main.notification

import com.currentweather.data.CurrentWeatherRepositoryImpl
import com.currentweather.data.LocationRepositoryImpl
import com.currentweather.domain.CurrentWeatherRepository
import com.currentweather.domain.LocationRepository
import com.currentweather.domain.OnGoingRefreshRepository
import com.wunderground.android.weather.app.status_bar_notifications_manager.StatusBarRefreshRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val notificationSettingsViewModule = module {
    scope(named<NotificationSettingsFragment>()){
        viewModel { NotificationSettingsViewModel(get(), get(), get(), get(), get()) }
    }
}

val notificationRepositoryModule = module {
    scope(named<NotificationSettingsFragment>()) {
        scoped<OnGoingRefreshRepository> {
            StatusBarRefreshRepositoryImpl(
                androidContext(),
                get()
            )
        }
        scoped<LocationRepository> {LocationRepositoryImpl(get(), get(), get()) }
        scoped<CurrentWeatherRepository> {CurrentWeatherRepositoryImpl(get()) }
    }
}