package com.currentweather.ui.main.units

import android.util.Log
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

class UnitsViewModel(val unitsRepository: UnitsRepository, val coroutineContextProvider: CoroutineContextProvider) : BaseObservableViewModel(), LifecycleObserver {
    private val units by lazy {
        MutableLiveData<List<Unit>>()
    }

    val onPositionClickListener = object : OnPositionClickListener {
        override fun onClick(position: Int) {
            Log.d("", "")
        }
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
