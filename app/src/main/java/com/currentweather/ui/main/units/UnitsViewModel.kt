package com.currentweather.ui.main.units

import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.currentweather.BR
import com.currentweather.CoroutineContextProvider
import com.currentweather.domain.UnitsRepository
import com.currentweather.domain.model.Unit
import com.currentweather.ui.base.BaseObservableViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UnitsViewModel(val unitsRepository: UnitsRepository, val coroutineContextProvider: CoroutineContextProvider) : BaseObservableViewModel(), LifecycleObserver {
    private val units by lazy {
        MutableLiveData<List<Unit>>()
    }

    @Bindable
    fun getViewModelLiveData(): LiveData<List<Unit>> = units

    fun setItems(unitsList: List<Unit>) {
        units.value = unitsList
        notifyPropertyChanged(BR.viewModelLiveData)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadItems(){
        viewModelScope.launch(handler) {
            val items = withContext(coroutineContextProvider.io()) {
                unitsRepository.getAvailableUnits()
            }
            setItems(items)
        }
    }

    override fun handleException(exception: Throwable) {
        //TODO("not implemented")
    }
}
