package com.amasmobile.jet_weather_forecast.screens.main

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amasmobile.jet_weather_forecast.data.DataOrException
import com.amasmobile.jet_weather_forecast.models.Weather
import com.amasmobile.jet_weather_forecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {

    suspend fun getWeatherData(city: String, units: String) : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units = units)
    }
}