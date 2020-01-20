package com.currentweather

import kotlinx.coroutines.Dispatchers

class TestContextProvider : CoroutineContextProvider {
    override fun main() = Dispatchers.Unconfined
    override fun io() = Dispatchers.Unconfined
}