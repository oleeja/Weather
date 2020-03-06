package com.currentweather.ui.notification_ui

import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.location.Location
import android.os.Build
import com.currentweather.R
import com.currentweather.domain.CurrentWeatherRepository
import com.currentweather.domain.LocationRepository
import com.currentweather.domain.model.WeatherModel
import com.currentweather.domain.model.notification.NotificationSettings
import com.currentweather.utils.*


class NotificationUiHandler(
    private val context: Context,
    private val statusBarIconUtils: StatusBarIconUtils,
    private val notificationUtils: NotificationUtils,
    private val currentWeatherRepository: CurrentWeatherRepository,
    private val locationRepository: LocationRepository,
    private val notificationSettings: NotificationSettings,
    private val drawableUtils: DrawableUtils
) : OnGoingNotificationHandler {

    private val ONGOING_NOTIFICATION_ID = 101

    private var updateTime: Long = 0

    override suspend fun showNotification() {
        //TODO Change to notification location
        val appLocation = locationRepository.getLocation()
        val weatherModel = currentWeatherRepository.getWeatherData(appLocation)
        showNotificationData(
            weatherModel.toOntoOnGoingNotificationModel(appLocation),
            appLocation.getDisplayingName(context)
        )
    }

    override suspend fun dismissNotification() {
        (context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager).cancel(
            ONGOING_NOTIFICATION_ID
        )
    }

    override val isNotificationEnable = notificationSettings.isStatusBarNotificationEnable

    private fun showNotificationData(
        onGoingNotificationDataModel: OnGoingNotificationDataModel,
        title: String
    ) {
        onGoingNotificationDataModel.let {
            val icon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Icon.createWithBitmap(statusBarIconUtils.getTemperatureBitmap(it.temperature))
            } else null

            updateTime = onGoingNotificationDataModel.updatedTime ?: System.currentTimeMillis()

            notificationUtils.createCustomNotification(
                icon,
                drawableUtils.getBitmapFromVectorDrawable(
                    getLocalIconFromIconCode(
                        onGoingNotificationDataModel.iconCode
                    ), 24f, 24f
                )
                    ?: BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher),
                title,
                onGoingNotificationDataModel.tempMax.toString() + "," + onGoingNotificationDataModel.tempMax,
                ONGOING_NOTIFICATION_ID,
                it.locationInfo
            )
        }
    }

    private fun WeatherModel.toOntoOnGoingNotificationModel(location: Location) =
        OnGoingNotificationDataModel(
            main.temp.toInt(),
            main.tempMax.toInt(),
            main.tempMin.toInt(),
            main.humidity,
            weather[0].icon,
            location,
            updateTime
        )

}