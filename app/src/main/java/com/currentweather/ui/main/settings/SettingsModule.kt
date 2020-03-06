package com.currentweather.ui.main.settings

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val settingsViewModule = module{
    scope(named<SettingsFragment>()){
        viewModel { SettingsViewModel() }
    }
}