package com.currentweather.ui.main.location_picker

import com.currentweather.ui.views.AbstractMapView
import com.currentweather.ui.views.GoogleMapView
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val locationPickerViewModule = module {
    factory<AbstractMapView> { GoogleMapView(androidContext()) }
    viewModel { LocationPickerViewModel(get(), get()) }
}