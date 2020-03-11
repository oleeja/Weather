package com.currentweather.ui.main.location_picker

import com.currentweather.data.LocationRepositoryImpl
import com.currentweather.data.OnGoingLocationRepositoryImpl
import com.currentweather.domain.LocationRepository
import com.currentweather.ui.views.AbstractMapView
import com.currentweather.ui.views.GoogleMapView
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val locationPickerViewModule = module {
    scope(named<LocationPickerFragment>()) {
        scoped<AbstractMapView> { GoogleMapView(androidContext()) }
        viewModel { LocationPickerViewModel(get(), get(parameters = {it})) }
    }
}

val locationPickerRepositoryModule = module {
    scope(named<LocationPickerFragment>()) {
        scoped<LocationRepository> { (params: LocationPickerFragment.LocationType) ->
            when (params) {
                LocationPickerFragment.LocationType.APP_LOCATION -> LocationRepositoryImpl(
                    get(),
                    get(),
                    get()
                )
                LocationPickerFragment.LocationType.ONGOING_NOTIFICATION_LOCATION -> OnGoingLocationRepositoryImpl(
                    get(),
                    get(),
                    get()
                )
                LocationPickerFragment.LocationType.WIDGET_LOCATION -> LocationRepositoryImpl(
                    get(),
                    get(),
                    get()
                )
            }
        }
    }
}