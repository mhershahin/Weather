package com.service.entity.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherCacheEntity(
    @PrimaryKey val locationId: Int,
    val payloadJson: String,
    val fetchedAtMillis: Long,
)
