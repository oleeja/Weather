package com.currentweather.ui.views

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.widget.FrameLayout
import com.google.android.gms.maps.model.LatLng

abstract class AbstractMapView: FrameLayout {

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

    abstract fun onStart()
    abstract fun onCreate(savedInstanceState: Bundle?)
    abstract fun onResume()
    abstract fun onPause()
    abstract fun onStop()
    abstract fun onDestroy()
    abstract fun onSaveInstanceState(savedInstanceState: Bundle?)
    abstract fun onLowMemory()

    abstract fun setMapPaddings(left: Int, top: Int, right: Int, bottom: Int)

    abstract fun setPosition(latLng: LatLng)

    abstract fun addPickLocationListener(listener: (LatLng) -> Unit)

    abstract fun setZoom(zoomLevel: Float)
}