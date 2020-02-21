package com.currentweather

import android.app.Application
import com.currentweather.ui.di.commonModule
import com.currentweather.ui.di.dataSourceModule
import com.currentweather.ui.main.current_weather.weatherRepositoryModule
import com.currentweather.ui.main.current_weather.weatherViewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(commonModule + dataSourceModule)
            modules(weatherViewModule + weatherRepositoryModule)
        }
    }
}