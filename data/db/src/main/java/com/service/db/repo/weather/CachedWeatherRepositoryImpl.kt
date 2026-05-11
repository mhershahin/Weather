package com.service.db.repo.weather

import com.service.db.dao.WeatherCacheDao
import com.service.entity.Result
import com.service.entity.db.WeatherCacheEntity
import com.service.entity.domain.Location
import com.service.entity.domain.Weather
import com.service.entity.domain.toWeather
import com.service.entity.response.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CachedWeatherRepositoryImpl @Inject constructor(
    private val dao: WeatherCacheDao
) : CachedWeatherRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
    }

    override fun observeForLocation(locationId: Int): Flow<Weather?> =
        dao.observeForLocation(locationId).map { entity ->
            entity?.let { runCatching { json.decodeFromString(Weather.serializer(), it.payloadJson) }.getOrNull() }
        }

    override suspend fun refresh(location: Location,weather: Result<WeatherResponse>): Result<Unit> {
        return when (weather) {
            is Result.Success -> {
                val payload = weather.data?.toWeather(location.id, System.currentTimeMillis())
                if (payload != null) {
                    dao.upsert(
                        WeatherCacheEntity(
                            locationId = location.id,
                            payloadJson = json.encodeToString(Weather.serializer(), payload),
                            fetchedAtMillis = payload.fetchedAtMillis,
                        )
                    )
                }
                Result.Success(Unit)
            }
            is Result.Error -> Result.Error(weather.code, weather.message)
        }
    }
}
