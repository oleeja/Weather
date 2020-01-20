package com.currentweather

import kotlinx.coroutines.Dispatchers

class TestContextProvider : DispatcherProvider {
    override fun main() = Dispatchers.Unconfined
    override fun io() = Dispatchers.Unconfined
}