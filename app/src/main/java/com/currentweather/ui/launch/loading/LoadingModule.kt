package com.currentweather.ui.launch.loading

import com.currentweather.data.LocationRepositoryImpl
import com.currentweather.domain.LocationRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val loadingViewModule = module {
    scope(named<LoadingFragment>()){
        viewModel { LoadingViewModel(get(), get()) }
    }
}

val loadingRepositoryModule = module {
    scope(named<LoadingFragment>()){
        scoped<LocationRepository> { LocationRepositoryImpl(get(), get(), get()) }
    }
}