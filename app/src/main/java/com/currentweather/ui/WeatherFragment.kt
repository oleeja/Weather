package com.currentweather.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.currentweather.databinding.FragmentWeatherBinding

class WeatherFragment: Fragment() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = WeatherViewModel()

        val binding = FragmentWeatherBinding.inflate(inflater, container, false)
        context ?: return binding.root

        subscribeUi(binding)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentWeatherBinding) {
        viewModel.weather.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is WeatherViewModel.ViewState.Success -> {
                    Log.d("tttag", result.toString())
                }
                is WeatherViewModel.ViewState.Error -> {
                    Log.d("tttage", result.toString())
                }
                is WeatherViewModel.ViewState.Loading -> {

                }
            }
        })
    }

    companion object{
        fun newInstance() = WeatherFragment()
    }
}