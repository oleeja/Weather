package com.currentweather.ui.di

import com.currentweather.CoroutineContextProvider
import com.currentweather.DefaultCoroutineContextProvider
import com.currentweather.data_sources.AppLocationDataSource
import com.currentweather.data_sources.LastKnownLocationDataSource
import com.currentweather.data_sources.UnitsDataSource
import com.currentweather.data_sources.UpdateLocationDataSource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module{
    single<CoroutineContextProvider> { DefaultCoroutineContextProvider() }
    single<FusedLocationProviderClient>{ LocationServices.getFusedLocationProviderClient(androidContext())}
}

val dataSourceModule = module{
    single{ LastKnownLocationDataSource(get())}
    single{ UpdateLocationDataSource(get())}
    single { UnitsDataSource(androidContext()) }
    single { AppLocationDataSource(androidContext()) }
}