package com.currentweather.ui.main.location_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.currentweather.R
import com.currentweather.databinding.FragmentLocationPickerBinding
import com.currentweather.ui.launch.loading.LoadingFragment
import com.currentweather.ui.main.notification.NotificationSettingsFragment
import com.currentweather.ui.views.AbstractMapView
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_location_picker.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


class LocationPickerFragment : Fragment() {

    private val map: AbstractMapView by currentScope.inject()

    private lateinit var locationPickerViewModel: LocationPickerViewModel

    private val viewModelScope = getKoin().getOrCreateScope("LocationPickerFragment", named<LocationPickerFragment>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setPickupResult()
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            setPickupResult()
            false
        } else super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLocationPickerBinding.inflate(inflater, container, false).also {
        val backStack = findNavController().backStack
        val parameters = {
            parametersOf(
                when (backStack.elementAt(backStack.size-2).destination.label) {
                    LoadingFragment::class.java.simpleName -> LocationType.APP_LOCATION
                    NotificationSettingsFragment::class.java.simpleName  -> LocationType.ONGOING_NOTIFICATION_LOCATION
                    else -> LocationType.APP_LOCATION
                }
            )
        }
        this.locationPickerViewModel = viewModelScope.get(parameters = parameters)
        lifecycle.addObserver(locationPickerViewModel)
        it.mapContainer.addView(map.apply {
            addPickLocationListener { latLng -> locationPickerViewModel.setLocation(latLng) }
        })
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.pick_place_title)
        locationPickerViewModel.currentWeatherData.observe(viewLifecycleOwner, Observer { result ->
            map.setPosition(LatLng(result.latitude, result.longitude))
            map.setZoom(INITIAL_ZOOM_LEVEL)
        })
    }

    override fun onStart() {
        super.onStart()
        map.onStart()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
    }

    override fun onDestroyView() {
        mapContainer.removeView(map)
        map.onDestroy()
        viewModelScope.close()
        super.onDestroyView()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        map.onSaveInstanceState(savedInstanceState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    private fun setPickupResult() {
        locationPickerViewModel.getLocation().let {
            locationPickerViewModel.changeLocation(it)
        }
    }

    enum class LocationType {
        APP_LOCATION, ONGOING_NOTIFICATION_LOCATION, WIDGET_LOCATION
    }

    companion object {
        private const val INITIAL_ZOOM_LEVEL = 7f
    }
}
