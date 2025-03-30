package com.amasmobile.jet_weather_forecast.repository

import android.util.Log
import com.amasmobile.jet_weather_forecast.data.DataOrException
import com.amasmobile.jet_weather_forecast.models.Weather
import com.amasmobile.jet_weather_forecast.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherAPI){
    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception>{
        val response =
            try {
                api.getWeather(query = cityQuery)
            }
            catch (e: Exception){
                return DataOrException(e = e)
            }
        return DataOrException(data = response)
    }
}