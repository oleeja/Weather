package com.currentweather.utils.permissions


import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context

interface RevokedPermissionsDelegate {

    fun handleRevokedPermission(context: Context, event: OnRequestPermissionsResultEvent)
}

internal class RevokedPermissionsDelegateImpl(
        /*private val dialogHelper: DialogHelper,
        private val settingsJumper: AppSettingsJumper*/) : RevokedPermissionsDelegate {

    private val settingsJump by lazy { { /*settingsJumper.jumpToSettingsPermissions()*/ } }

    override fun handleRevokedPermission(context: Context, event: OnRequestPermissionsResultEvent) {
        when {
            event.isAllGranted -> return
            isGalleryPermissionDenied(event) -> {
                //dialogHelper.showDialogPermissionGallery(context, settingsJump)
            }
            isCameraPermissionDenied(event) -> {
                //dialogHelper.showDialogPermissionCamera(context, settingsJump)
            }
        }
    }

    private fun isGalleryPermissionDenied(event: OnRequestPermissionsResultEvent): Boolean =
            isDenied(Permissions.GALLERY, event)

    private fun isCameraPermissionDenied(event: OnRequestPermissionsResultEvent): Boolean =
            isDenied(Permissions.CAMERA, event)


    private fun isDenied(permission: Permissions, event: OnRequestPermissionsResultEvent): Boolean =
            with(event) {
                val permissions = permission.permissions
                event.containsPermissions(permissions)
                        && event.isDenied(permissions)
            }


}

enum class Permissions constructor(val permissions: List<String>) {

    GALLERY(listOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)),
    CAMERA(listOf(android.Manifest.permission.CAMERA, WRITE_EXTERNAL_STORAGE));

}