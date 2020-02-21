package com.currentweather.utils.permissions

/**
 * Custom permission exception.
 *
 * @param message - exception text
 * @param result - [RequestPermissionsResult], container with information permissions response
 * */
class PermissionException(message: String, val result: RequestPermissionsResult) : IllegalStateException(message)