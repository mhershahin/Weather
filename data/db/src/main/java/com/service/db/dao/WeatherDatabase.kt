package com.service.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.service.entity.db.LocationEntity
import com.service.entity.db.WeatherCacheEntity

@Database(
    entities = [LocationEntity::class, WeatherCacheEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun weatherCacheDao(): WeatherCacheDao
}
