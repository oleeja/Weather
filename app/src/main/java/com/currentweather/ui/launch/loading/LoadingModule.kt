package com.currentweather.ui.launch.loading

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loadingViewModule = module {
    viewModel { LoadingViewModel(get(), get()) }
}