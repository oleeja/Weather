package com.currentweather.ui.main.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.currentweather.ui.base.BaseViewModel

class SettingsViewModel : BaseViewModel() {

    private val settings by lazy {
        MutableLiveData<Settings>()
    }

    fun getViewModelLiveData(): LiveData<Settings> = settings

    fun onUnitsClicked(){
        settings.value = Settings.UNITS
        settings.value = Settings.NONE
    }


    enum class Settings{
        UNITS, NONE
    }

    override fun handleException(exception: Throwable) {
        //TODO("not implemented")
    }
}
