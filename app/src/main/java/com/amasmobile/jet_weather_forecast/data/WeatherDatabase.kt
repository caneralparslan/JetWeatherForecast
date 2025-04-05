package com.amasmobile.jet_weather_forecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amasmobile.jet_weather_forecast.models.Favorite
import com.amasmobile.jet_weather_forecast.models.Unit


@Database(entities = [Favorite::class, Unit::class], version = 4, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}