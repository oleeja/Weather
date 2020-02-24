package com.currentweather.ui.main.units

import com.currentweather.data.UnitsRepositoryImpl
import com.currentweather.domain.UnitsRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val unitsViewModule = module{
    viewModel { UnitsViewModel(get(), get()) }
}

val unitsRepositoryModule = module {
    single<UnitsRepository> { UnitsRepositoryImpl(get()) }
}