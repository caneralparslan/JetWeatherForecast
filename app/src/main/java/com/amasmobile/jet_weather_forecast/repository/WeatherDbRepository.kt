package com.amasmobile.jet_weather_forecast.repository

import com.amasmobile.jet_weather_forecast.data.WeatherDao
import com.amasmobile.jet_weather_forecast.models.Favorite
import com.amasmobile.jet_weather_forecast.models.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites() : Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun getFavoriteByCity(city: String) : Favorite? = weatherDao.getFavoriteByCity(city)
    suspend fun addFavorite(favorite: Favorite) = weatherDao.addFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun isFavorite(city: String) = weatherDao.isFavorite(city)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun removeFavorite(favorite: Favorite) = weatherDao.removeFavorite(favorite)

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun addUnit(unit: Unit) = weatherDao.addUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun removeUnit(unit: Unit) = weatherDao.removeUnit(unit)

}