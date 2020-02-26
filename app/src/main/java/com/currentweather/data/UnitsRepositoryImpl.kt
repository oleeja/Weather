package com.currentweather.data

import com.currentweather.data_sources.UnitsDataSource
import com.currentweather.domain.UnitsRepository
import com.currentweather.domain.model.Unit

class UnitsRepositoryImpl(private val unitsDataSource: UnitsDataSource) : UnitsRepository {

    override suspend fun getAvailableUnits() = unitsDataSource.getUnits().apply {
        this[this.indexOfFirst { unit -> unit.key == unitsDataSource.getAppUnit().key }] =
            unitsDataSource.getAppUnit()
    }

    override suspend fun setAppUnits(unit: Unit) {
        unitsDataSource.saveAppUnit(unit)
    }
}