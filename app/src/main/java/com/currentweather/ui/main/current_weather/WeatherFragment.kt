package com.currentweather.ui.main.current_weather

import android.Manifest
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.currentweather.R
import com.currentweather.databinding.FragmentWeatherBinding
import com.currentweather.domain.model.WeatherModel
import com.currentweather.domain.model.forecast.ForecastThreeHours
import com.currentweather.domain.model.forecast.ForecastThreeHoursModel
import com.currentweather.ui.base.BaseFragment
import com.currentweather.utils.getResourceBackgroundMain
import com.currentweather.utils.getResourceBackgroundSecondary
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class WeatherFragment : BaseFragment() {

    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWeatherBinding.inflate(inflater, container, false).also {
        lifecycle.addObserver(weatherViewModel)
        subscribeUi(it)
    }.root

    private fun subscribeUi(binding: FragmentWeatherBinding) {
        GlobalScope.launch(Dispatchers.Main) {
            val permissionResult =
                permissions.requestPermissions(listOf(Manifest.permission.ACCESS_COARSE_LOCATION))
            if (permissionResult.isAllGranted) {
                weatherViewModel.getData()
            } else {
                //TODO: add permissions denied handler
            }
        }

        weatherViewModel.getViewModelLiveData().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is WeatherViewModel.ViewState.Success -> {
                    when (result.data) {
                        is WeatherModel -> {
                            binding.model = result.data
                            setBarColors(result.data.dt)

                        }
                        is ForecastThreeHoursModel -> {
                            forecastRecyclerWeather.layoutManager =
                                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                            LastAdapter(result.data.list, BR.model)
                                .map<ForecastThreeHours>(R.layout.item_forecast_three_hours)
                                .into(forecastRecyclerWeather)
                        }
                        is Location -> {
                            //TODO: move geocoding to datasource
                            val addresses: List<Address> =
                                Geocoder(context, Locale.getDefault()).getFromLocation(result.data.latitude, result.data.longitude, 1)
                            if(addresses.isNotEmpty()){
                                (activity as AppCompatActivity).supportActionBar?.title = addresses[0].locality
                            }
                        }
                        else -> {
                        }
                    }
                }
                is WeatherViewModel.ViewState.Error -> {
                    Log.d("tttage", result.toString())
                }
            }
        })
    }

    private fun setBarColors(timestamp: Long) {
        context?.let {
            (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(it, getResourceBackgroundMain(timestamp))
                )
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity?.window?.apply {
                    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    statusBarColor =
                        ContextCompat.getColor(it, getResourceBackgroundSecondary(timestamp))
                }
            }
        }
    }
}