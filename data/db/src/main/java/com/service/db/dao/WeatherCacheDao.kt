package com.service.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.service.entity.db.WeatherCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherCacheDao {

    @Query("SELECT * FROM weather_cache WHERE locationId = :locationId LIMIT 1")
    fun observeForLocation(locationId: Int): Flow<WeatherCacheEntity?>

    @Query("SELECT * FROM weather_cache")
    fun observeAll(): Flow<List<WeatherCacheEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: WeatherCacheEntity): Long

    @Query("DELETE FROM weather_cache WHERE locationId = :locationId")
    suspend fun deleteForLocation(locationId: Int): Int
}
