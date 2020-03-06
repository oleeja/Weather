package com.currentweather.ui.main.current_weather

import com.currentweather.data.CurrentWeatherRepositoryImpl
import com.currentweather.data.ForecastThreeHoursRepositoryImpl
import com.currentweather.data.LocationRepositoryImpl
import com.currentweather.data.UnitsRepositoryImpl
import com.currentweather.domain.CurrentWeatherRepository
import com.currentweather.domain.ForecastRepository
import com.currentweather.domain.LocationRepository
import com.currentweather.domain.UnitsRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val weatherViewModule = module{
    scope(named<WeatherFragment>()){
        viewModel { WeatherViewModel(get(), get(), get(), get(), get()) }
    }
}

val weatherRepositoryModule = module {
    scope(named<WeatherFragment>()){
        scoped<CurrentWeatherRepository> { CurrentWeatherRepositoryImpl(get()) }
        scoped<ForecastRepository> { ForecastThreeHoursRepositoryImpl(get()) }
        scoped<LocationRepository> { LocationRepositoryImpl(get(), get(), get()) }
        scoped<UnitsRepository> { UnitsRepositoryImpl(get()) }
    }
}