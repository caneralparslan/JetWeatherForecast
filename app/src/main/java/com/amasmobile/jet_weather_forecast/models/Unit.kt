package com.amasmobile.jet_weather_forecast.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "units_table")
data class Unit(

    @ColumnInfo
    @PrimaryKey
    val unit: String
)
