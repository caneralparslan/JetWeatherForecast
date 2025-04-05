package com.amasmobile.jet_weather_forecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amasmobile.jet_weather_forecast.models.Unit
import com.amasmobile.jet_weather_forecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel() {

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.getUnits().distinctUntilChanged()
                .collect {
                    listOfUnits ->
                    if (listOfUnits.isEmpty()){
                        Log.d("Empty Units List", "List of Units is empty!")
                    }
                    else{
                        _unitList.value = listOfUnits
                        Log.d("Units List", "Units List: ${unitList.value} ")
                    }
                }
        }
    }

    fun addUnit(unit: Unit) = viewModelScope.launch {
        repository.addUnit(unit)
    }

    fun removeUnit(unit: Unit) = viewModelScope.launch {
        repository.removeUnit(unit)
    }

    fun updateUnit(unit: Unit) = viewModelScope.launch {
        repository.updateUnit(unit)
    }

    fun deleteAllUnits() = viewModelScope.launch {
        repository.deleteAllUnits()
    }
}