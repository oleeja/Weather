package com.currentweather.domain.model.forecast

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currentweather.domain.model.Coord
import com.google.gson.annotations.SerializedName

@Entity
data class City(
    @PrimaryKey
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("timezone")
    val timezone: Int
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is City) return false

        if (coord != other.coord) return false

        return true
    }

    override fun hashCode() = coord.hashCode()
}