package com.amasmobile.jet_weather_forecast.network

import com.amasmobile.jet_weather_forecast.models.Weather
import com.amasmobile.jet_weather_forecast.models.WeatherObject
import com.amasmobile.jet_weather_forecast.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface WeatherAPI {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY
    ): Weather

}