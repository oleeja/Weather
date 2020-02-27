package com.currentweather.ui.views

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class GoogleMapView : AbstractMapView {

    private var mapView: MapView
    private var googleMap: GoogleMap? = null

    private var mapPaddingLeft = 0
    private var mapPaddingTop = 0
    private var mapPaddingRight = 0
    private var mapPaddingBottom = 0

    private var currentWidth = 0
    private var currentHeight = 0

    private var pickLocationListener: ((LatLng) -> Unit)? = null
    private var initialPosition: LatLng? = null
    private var initialZoomLevel: Float? = null

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    init {
        currentWidth = resources.displayMetrics.widthPixels
        currentHeight = resources.displayMetrics.heightPixels

        mapView = MapView(context).apply {
            getMapAsync { it?.let { setupMap(it) } }
        }

        addView(
            mapView, LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        mapView.isClickable = true
    }

    override fun setMapPaddings(left: Int, top: Int, right: Int, bottom: Int) {
        mapPaddingLeft = left
        mapPaddingTop = top
        mapPaddingRight = right
        mapPaddingBottom = bottom
        googleMap?.setPadding(mapPaddingLeft, mapPaddingTop, mapPaddingRight, mapPaddingBottom)
    }

    override fun onStart() {
        mapView.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
    }

    override fun onResume() {
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
    }

    override fun onStop() {
        mapView.onStop()
    }

    override fun onDestroy() {
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        mapView.onSaveInstanceState(savedInstanceState)
    }

    override fun onLowMemory() {
        mapView.onLowMemory()
    }

    override fun setPosition(latLng: LatLng) {
        initialPosition = latLng
        if (googleMap == null) addNewMarker(latLng, initialZoomLevel)
    }

    override fun addPickLocationListener(listener: (LatLng) -> Unit) {
        pickLocationListener = listener
    }

    override fun setZoom(zoomLevel: Float) {
        initialZoomLevel = zoomLevel
        if (googleMap != null) initialPosition?.let {
            addNewMarker(it, zoomLevel)
        }
    }

    private fun setupMap(googleMap: GoogleMap) {
        this.googleMap = googleMap
        initialPosition?.let { addNewMarker(it, initialZoomLevel) }
        googleMap.setOnMapClickListener {
            addNewMarker(it)
            pickLocationListener?.invoke(it)
        }
    }

    private fun addNewMarker(latLng: LatLng, zoomLevel: Float? = null) {
        googleMap?.clear()
        googleMap?.addMarker(MarkerOptions().position(latLng))
        googleMap?.animateCamera(
            if (zoomLevel == null) CameraUpdateFactory.newLatLng(latLng) else CameraUpdateFactory.newLatLngZoom(
                latLng,
                zoomLevel
            )
        )
    }
}