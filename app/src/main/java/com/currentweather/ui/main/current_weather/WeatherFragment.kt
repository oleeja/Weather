package com.currentweather.ui.main.current_weather

import android.Manifest
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
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

    private var menuItemColor = Color.BLACK

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWeatherBinding.inflate(inflater, container, false).also {
        lifecycle.addObserver(weatherViewModel)
        subscribeUi(it)
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem) =
        item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.forEach {
            val s = SpannableString(it.title)
            s.setSpan(ForegroundColorSpan(menuItemColor), 0, s.length, 0)
            it.title = s
        }
        super.onPrepareOptionsMenu(menu)
    }

    private fun setBarColors(timestamp: Long) {
        context?.let {
            (activity as AppCompatActivity).supportActionBar?.apply {
                setBackgroundDrawable(
                    ColorDrawable(
                        ContextCompat.getColor(it, getResourceBackgroundMain(timestamp).also { menuItemColor = it })
                    )
                )
                invalidateOptionsMenu()
            }
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