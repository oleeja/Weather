package com.currentweather.ui.base

import android.os.Build
import androidx.fragment.app.Fragment
import com.currentweather.utils.permissions.*

abstract class BaseFragment : Fragment() {

    /**
     * Emits [OnRequestPermissionsResultEvent]
     * */
    private val onRequestPermissionsResultDelegateEmitter: EmitableCompositeEventListener<OnRequestPermissionsResultEvent> = SimpleCompositeEventListener()
    /**
     * Use it to subscribe on [OnRequestPermissionsResultEvent]
     * */
    private val onRequestPermissionsResultDelegate: CompositeEventListener<OnRequestPermissionsResultEvent> = onRequestPermissionsResultDelegateEmitter
    /**
     * Lazy initialization of [PermissionsManagerDelegate]
     * */
    val permissions: PermissionsManager by lazy {
        PermissionsManagerDelegate(onRequestPermissionsResultDelegate, view = { this })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val shouldShowRequestPermissionRationale = BooleanArray(permissions.size) {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                permissions[it])
        }

        val result = OnRequestPermissionsResultEvent(
            requestCode,
            permissions.toList(),
            grantResults.toList(),
            shouldShowRequestPermissionRationale.toList())
        onRequestPermissionsResultDelegateEmitter.emit(result)
    }
}