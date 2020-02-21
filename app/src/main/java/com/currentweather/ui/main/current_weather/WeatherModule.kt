package com.currentweather.ui.main.current_weather

import com.currentweather.data.CurrentWeatherRepositoryImpl
import com.currentweather.data.ForecastThreeHoursRepositoryImpl
import com.currentweather.data.LocationRepositoryImpl
import com.currentweather.domain.CurrentWeatherRepository
import com.currentweather.domain.ForecastRepository
import com.currentweather.domain.LocationRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weatherViewModule = module{
    viewModel { WeatherViewModel(get(), get(), get(), get()) }
}

val weatherRepositoryModule = module {
    single<CurrentWeatherRepository> { CurrentWeatherRepositoryImpl(get()) }
    single<ForecastRepository> { ForecastThreeHoursRepositoryImpl(get()) }
    single<LocationRepository> { LocationRepositoryImpl(get(), get()) }
}