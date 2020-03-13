package com.currentweather.ui.notification_ui

import com.currentweather.data.CurrentWeatherRepositoryImpl
import com.currentweather.data.OnGoingLocationRepositoryImpl
import com.currentweather.domain.CurrentWeatherRepository
import com.currentweather.domain.LocationRepository
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID
import org.koin.dsl.module


val notificationBackgroundModule = module {
    scope(named<NotificationUiHandler>()) {
        scoped <CurrentWeatherRepository> { CurrentWeatherRepositoryImpl(get(), get()) }
        scoped <LocationRepository> { OnGoingLocationRepositoryImpl(get(), get(), get()) }
    }
}

val notificationBackgroundScopeId: ScopeID = "notificationBackgroundScopeId"
