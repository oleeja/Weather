package com.currentweather.ui.main.location_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.currentweather.databinding.FragmentLocationPickerBinding
import com.currentweather.ui.views.AbstractMapView
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_location_picker.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LocationPickerFragment : Fragment() {

    private val map: AbstractMapView by inject()

    private val locationPickerViewModel: LocationPickerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
               setPickupResult()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == android.R.id.home){
            setPickupResult()
            false
        }
        else super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLocationPickerBinding.inflate(inflater,  container, false).also {
        lifecycle.addObserver(locationPickerViewModel)
        it.mapContainer.addView(map.apply {
            addPickLocationListener { locationPickerViewModel.setLocation(it) }
        })
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Pick Place"
        locationPickerViewModel.currentWeatherData.observe(viewLifecycleOwner, Observer { result ->
            map.setPosition(LatLng(result.latitude, result.longitude))
            map.setZoom(INITIAL_ZOOM_LEVEL)
        })
    }

    override fun onStart(){
        super.onStart()
        map.onStart()
    }

    override fun onResume(){
        super.onResume()
        map.onResume()
    }

    override fun onPause(){
        super.onPause()
        map.onPause()
    }

    override fun onStop(){
        super.onStop()
        map.onStop()
    }

    override fun onDestroyView() {
        mapContainer.removeView(map)
        map.onDestroy()
        super.onDestroyView()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle){
        super.onSaveInstanceState(savedInstanceState)
        map.onSaveInstanceState(savedInstanceState)
    }

    override fun onLowMemory(){
        super.onLowMemory()
        map.onLowMemory()
    }


    private fun setPickupResult (){
        locationPickerViewModel.getLocation().let {
            locationPickerViewModel.changeLocation(it)
        }
    }

    companion object{
        val PICK_LOCATION_KEY = "PICK_LOCATION_KEY"
        private val INITIAL_ZOOM_LEVEL = 7f
    }
}
