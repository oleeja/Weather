package com.currentweather.ui.main.settings

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsViewModule = module{
    viewModel { SettingsViewModel() }
}