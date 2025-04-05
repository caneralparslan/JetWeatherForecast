package com.amasmobile.jet_weather_forecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.amasmobile.jet_weather_forecast.models.Favorite
import com.amasmobile.jet_weather_forecast.models.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM favorite_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM FAVORITE_TBL WHERE city =:city")
    suspend fun getFavoriteByCity(city: String): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM FAVORITE_TBL WHERE city = :city")
    suspend fun isFavorite(city: String): Boolean

    @Query("DELETE FROM favorite_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun removeFavorite(favorite: Favorite)


    //UNIT

    @Query("SELECT * FROM units_table")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE FROM units_table")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun removeUnit(unit: Unit)
}