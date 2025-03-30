package com.amasmobile.jet_weather_forecast.models

data class WeatherObject(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)