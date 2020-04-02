package com.currentweather.ui.di

import com.currentweather.CoroutineContextProvider
import com.currentweather.DefaultCoroutineContextProvider
import com.currentweather.dao.WeatherDatabase
import com.currentweather.data_sources.AppLocationDataSource
import com.currentweather.data_sources.LastKnownLocationDataSource
import com.currentweather.data_sources.UnitsDataSource
import com.currentweather.data_sources.UpdateLocationDataSource
import com.currentweather.domain.model.notification.NotificationSettings
import com.currentweather.ui.notification_ui.NotificationUiHandler
import com.currentweather.ui.notification_ui.OnGoingNotificationHandler
import com.currentweather.utils.DrawableUtils
import com.currentweather.utils.NotificationUtils
import com.currentweather.utils.StatusBarIconUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module {
    single<CoroutineContextProvider> { DefaultCoroutineContextProvider() }
    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(
            androidContext()
        )
    }
}

val dataSourceModule = module {
    single { LastKnownLocationDataSource(get()) }
    single { UpdateLocationDataSource(get()) }
    single { UnitsDataSource(androidContext()) }
    single { AppLocationDataSource(androidContext()) }
    single { WeatherDatabase.getInstance(androidContext()).currentWeatherDao() }
    single { WeatherDatabase.getInstance(androidContext()).forecastThreeHoursDao() }
}

val utilsModule = module {
    factory { NotificationUtils(androidContext()) }
    factory { DrawableUtils(androidContext()) }
}

val onGoingNotificationModule = module {
    factory<OnGoingNotificationHandler> {
        NotificationUiHandler(
            androidContext(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    factory { StatusBarIconUtils(androidContext()) }
    factory { NotificationSettings(androidContext()) }
}