package com.amasmobile.jet_weather_forecast.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amasmobile.jet_weather_forecast.models.City
import com.amasmobile.jet_weather_forecast.models.Favorite
import com.amasmobile.jet_weather_forecast.repository.WeatherDbRepository
import com.amasmobile.jet_weather_forecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel(){

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect{
                    listOfFavorites ->
                    if(listOfFavorites.isEmpty() ){
                        Log.d("List of Favorites", "List of Favorites is empty ")
                    }
                    else{
                        _favList.value = listOfFavorites
                        Log.d("FavList", "FavList: ${favList.value} ")
                    }
                }
        }
    }

    fun addFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.addFavorite(favorite)
    }

    fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.updateFavorite(favorite)
    }

    fun removeFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.removeFavorite(favorite)
    }
}