package com.currentweather.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.currentweather.DefaultCoroutineContextProvider
import com.currentweather.data.Repository
import com.currentweather.data.model.WeatherModel
import com.currentweather.databinding.FragmentWeatherBinding

class WeatherFragment: Fragment() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = WeatherViewModel(Repository(), DefaultCoroutineContextProvider())
        viewModel.getData()

        val binding = FragmentWeatherBinding.inflate(inflater, container, false)
        context ?: return binding.root

        subscribeUi(binding)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentWeatherBinding) {
        viewModel.getViewModelLiveData().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is WeatherViewModel.ViewState.Success -> {
                    binding.model = result.data as WeatherModel
                }
                is WeatherViewModel.ViewState.Error -> {
                    Log.d("tttage", result.toString())
                }
            }
        })
    }

    companion object{
        fun newInstance() = WeatherFragment()
    }
}