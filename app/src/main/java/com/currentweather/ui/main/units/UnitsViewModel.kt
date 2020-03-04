package com.currentweather.ui.main.units

import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.currentweather.BR
import com.currentweather.CoroutineContextProvider
import com.currentweather.domain.UnitsRepository
import com.currentweather.domain.model.Unit
import com.currentweather.ui.base.BaseObservableViewModel
import com.currentweather.utils.OnPositionClickListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UnitsViewModel(
    val unitsRepository: UnitsRepository,
    val coroutineContextProvider: CoroutineContextProvider
) : BaseObservableViewModel(), LifecycleObserver {
    private val units by lazy {
        MutableLiveData<List<Unit>>()
    }

    val onPositionClickListener = object : OnPositionClickListener {
        override fun onClick(position: Int) {
            setChosen(
                with(units.value?.indexOfFirst { it.chosen }) { if (this == -1) 0 else this } ?: 0,
                false)
            setChosen(position)
        }
    }

    @Bindable
    fun getViewModelLiveData(): LiveData<List<Unit>> = units

    fun setChosen(index: Int, value: Boolean = true) {
        units.value?.get(index)?.let {
            it.chosen = value
            viewModelScope.launch (handler) {
                withContext(coroutineContextProvider.io()) {
                    unitsRepository.setAppUnits(it)
                }
            }
        }
        notifyPropertyChanged(BR.viewModelLiveData)
    }

    fun setItems(unitsList: List<Unit>) {
        units.value = unitsList
        notifyPropertyChanged(BR.viewModelLiveData)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadItems() {
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
