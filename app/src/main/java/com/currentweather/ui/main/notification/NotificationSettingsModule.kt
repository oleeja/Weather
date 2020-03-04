package com.currentweather.ui.main.notification

import com.currentweather.domain.OnGoingRefreshRepository
import com.wunderground.android.weather.app.status_bar_notifications_manager.StatusBarRefreshRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notificationSettingsViewModule = module {
    viewModel { NotificationSettingsViewModel(get(), get(), get(), get(), get()) }
}

val notificationRepositoryModule = module {
    factory <OnGoingRefreshRepository>{ StatusBarRefreshRepositoryImpl(androidContext(), get()) }
}