package com.currentweather.ui.main.units

import com.currentweather.data.UnitsRepositoryImpl
import com.currentweather.domain.UnitsRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val unitsViewModule = module{
    scope(named<UnitsFragment>()){
        viewModel { UnitsViewModel(get(), get()) }
    }
}

val unitsRepositoryModule = module {
    scope(named<UnitsFragment>()){
        scoped<UnitsRepository> { UnitsRepositoryImpl(get()) }
    }
}