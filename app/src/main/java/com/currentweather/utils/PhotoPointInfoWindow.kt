package com.currentweather.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.currentweather.R
import kotlinx.android.synthetic.main.view_photo_point_info_window.view.*

/* Usage in xml example
        <PhotoPointInfoWindow
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:pickup_image="@android:drawable/ic_dialog_dialer"
            app:pickup_rating="4.5"
            app:pickup_name="PICKUP SPOT"
            app:pickup_rating_visibility="false"
            app:pickup_point="Rd No 55, Dhaka"/>
 */
class PhotoPointInfoWindow : CardView {

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

        addView(LayoutInflater.from(context).inflate(R.layout.view_photo_point_info_window, this, false))
        if (attrs != null && defStyleAttr == -1 && defStyleRes == -1) {
            val typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.PhotoPointInfoWindow,
                defStyleAttr, defStyleRes
            )

            typedArray.getDrawable(
                R.styleable.PhotoPointInfoWindow_pickup_image
            )?.let { setPickupImage(it) }

            typedArray.getFloat(
                R.styleable.PhotoPointInfoWindow_pickup_rating,
                -1f
            ).let { if(it!=-1f) setPickupRating(it) }

            typedArray.getString(
                R.styleable.PhotoPointInfoWindow_pickup_photo_name
            )?.let { setPickupNameText(it) }

            typedArray.getString(
                R.styleable.PhotoPointInfoWindow_pickup_photo_point
            )?.let { setPickupPointText(it) }

            typedArray.getBoolean(
                R.styleable.PhotoPointInfoWindow_pickup_rating_visibility, true
            ).let { setRatingVisibility(it) }

            typedArray.getBoolean(
                R.styleable.PhotoPointInfoWindow_pickup_photo_visibility, true
            ).let { setPhotoVisibility(it) }

            typedArray.recycle()
        }
    }

    /**
     * Shows pickup spot image
     * @param image drawable image
     */
    fun setPickupImage(image: Drawable) {
        userPhotoImage.setImageDrawable(image)
    }

    /**
     * Shows pickup spot ratin
     * @param rating string that shows rating
     */
    fun setPickupRating(rating: Float) {
        ratingText.text = rating.toString()
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

    fun setRatingVisibility(visible: Boolean){
        ratingText.visibility = if(visible) VISIBLE else GONE
    }

    fun setPhotoVisibility(visible: Boolean){
        pickupPhotoBackground.visibility = if(visible) VISIBLE else GONE
    }
}