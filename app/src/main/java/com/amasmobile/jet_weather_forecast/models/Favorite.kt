package com.amasmobile.jet_weather_forecast.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull


@Entity(tableName = "favorite_tbl")
data class Favorite(

    @PrimaryKey
    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "country")
    val country: String,

)
