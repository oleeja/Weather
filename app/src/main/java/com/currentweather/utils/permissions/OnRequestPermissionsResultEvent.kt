package com.currentweather.utils.permissions

import android.content.pm.PackageManager

/**
 *
 * @param requestCode - permissions request code
 * @param permissions - list of requested permissions
 * @param grantResults - the grant results for the corresponding permissions
 * @param shouldShowRequestPermissionRationale - says whether you should show UI with rationale
 * for every requesting a permission
 * */
data class OnRequestPermissionsResultEvent(
        val requestCode: Int,
        val permissions: List<String>,
        val grantResults: List<Int>,
        val shouldShowRequestPermissionRationale: List<Boolean>) {

    /**
     * Returns true if all permissions ara granted
     * */
    val isAllGranted: Boolean get() = grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }

    fun containsPermissions(permissions: List<String>): Boolean =
            this.permissions.containsAll(permissions)

    /**
     * Returns true if all permissions are denied
     *
     * @param permissions - permissions to check
     * */
    fun isDenied(permissions: List<String>): Boolean =
            grantResults.isNotEmpty() && permissions.all { !isGranted(it) }

    /**
     * Returns true if specific permission is granted
     *
     * @param permission - permission to check
     * */
    fun isGranted(permission: String): Boolean = permissions.contains(permission)
            && grantResults[permissions.indexOf(permission)] == PackageManager.PERMISSION_GRANTED

    /**
     * Returns true if specific permission needs explanation
     *
     * @param permission - permission to check
     * */
    fun isShouldShowRequestPermissionRationale(
            permission: String): Boolean = shouldShowRequestPermissionRationale[permissions.indexOf(
            permission)]

    /**
     * Returns true if any permission needs explanation
     */
    val isShouldShowRequestPermissionRationale: Boolean get() = shouldShowRequestPermissionRationale.any { it }
}