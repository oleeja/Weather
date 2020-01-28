package com.currentweather.utils

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.currentweather.R
import kotlinx.android.synthetic.main.view_point_info_window.view.*

/* Usage in xml example
        <TimePointInfoWindow
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:pickup_time="6"
            app:pickup_time_unit="min"
            app:pickup_time_background_color="@android:color/black"
            app:pickup_name="PICKUP SPOT"
            app:pickup_time_visibility="false"
            app:pickup_point="Rd No 55, Dhaka"/>
 */
class PointInfoWindow : CardView {

    constructor(context: Context) : super(context) {
        init(context, null, -1, -1)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs, -1, -1)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, -1)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {

        addView(LayoutInflater.from(context).inflate(R.layout.view_point_info_window, this, false))
        if (attrs != null && defStyleAttr == -1 && defStyleRes == -1) {
            val typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.PointInfoWindow,
                defStyleAttr, defStyleRes
            )

            typedArray.getInt(
                R.styleable.PointInfoWindow_pickup_time,
                -1
            ).let { if (it != -1) setPickupTime(it) }

            typedArray.getString(
                R.styleable.PointInfoWindow_pickup_time_unit
            )?.let { setPickupTimeUnit(it) }

            typedArray.getColor(
                R.styleable.PointInfoWindow_pickup_time_background_color,
                Color.BLACK
            ).let { setPickupTimeBackground(it) }

            typedArray.getString(
                R.styleable.PointInfoWindow_pickup_name
            )?.let { setPickupNameText(it) }

            typedArray.getString(
                R.styleable.PointInfoWindow_pickup_point
            )?.let { setPickupPointText(it) }

            typedArray.getBoolean(
                R.styleable.PointInfoWindow_pickup_time_visibility, true
            ).let { setTimeViewVisibility(it) }

            typedArray.recycle()
        }
    }

    /**
     * Shows pickup time
     * @param time digit that shows time left to pickup
     */
    fun setPickupTime(time: Int) {
        pickupTimeText.text = time.toString()
    }

    /**
     * Shows pickup time unit
     * @param timeUnit string that shows unit of time left to pickup
     */
    fun setPickupTimeUnit(timeUnit: String) {
        pickupTimeUnitText.text = timeUnit
    }

    /**
     * Sets background color of pickup time view
     * @param color of pickup time view
     */
    fun setPickupTimeBackground(color: Int) {
        pickupTimeBackground.setBackgroundColor(color)
    }

    /**
     * Shows pickup point type, e.g. "Pickup Spot", "Drop off Spot"
     * @param nameText string that represent pickup point type
     */
    fun setPickupNameText(nameText: String) {
        pickupNameText.visibility = VISIBLE
        pickupNameText.text = nameText
    }

    /**
     * Shows pickup point name
     * @param pointText string that represent pickup point name
     */
    fun setPickupPointText(pointText: String) {
        pickupPointText.text = pointText
    }

    fun setTimeViewVisibility(visible: Boolean){
        pickupTimeBackground.visibility = if(visible) VISIBLE else GONE
    }
}