package com.currentweather.domain

import com.currentweather.domain.model.Unit

interface UnitsRepository {
    suspend fun getAvailableUnits(): List<Unit>
    suspend fun setAppUnits(unit: Unit)
}