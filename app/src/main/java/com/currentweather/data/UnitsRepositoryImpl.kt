package com.currentweather.data

import com.currentweather.data_sources.UnitsDataSource
import com.currentweather.domain.UnitsRepository
import com.currentweather.domain.model.Unit

class UnitsRepositoryImpl(val unitsDataSource: UnitsDataSource) : UnitsRepository {
    override suspend fun getAvailableUnits(): List<Unit> {
        return unitsDataSource.getUnits()
    }

    override suspend fun setAppUnits(unit: Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}