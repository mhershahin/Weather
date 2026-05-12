package com.service.db.repo.weather

import com.service.db.dao.WeatherCacheDao
import com.service.entity.Result
import com.service.entity.db.WeatherCacheEntity
import com.service.entity.domain.Location
import com.service.entity.domain.Weather
import com.service.entity.domain.toWeather
import com.service.entity.response.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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

    private val cacheMutex = Mutex()

    override fun observeForLocation(locationId: Int): Flow<Weather?> =
        dao.observeForLocation(locationId).map { entity ->
            entity?.let { runCatching { json.decodeFromString(Weather.serializer(), it.payloadJson) }.getOrNull() }
        }

    override suspend fun refreshHourly(location: Location, weather: Result<WeatherResponse>): Result<Unit> =
        merge(location, weather) { existing, incoming ->

            existing?.copy(
                current = incoming.current,
                hourly = incoming.hourly,
                highC = incoming.highC,
                lowC = incoming.lowC,
                fetchedAtMillis = incoming.fetchedAtMillis,
            ) ?: incoming
        }

    override suspend fun refreshForecast(location: Location, weather: Result<WeatherResponse>): Result<Unit> =
        merge(location, weather) { existing, incoming ->
            existing?.copy(
                daily = incoming.daily,
            ) ?: incoming
        }

    private suspend fun merge(
        location: Location,
        weather: Result<WeatherResponse>,
        combine: (existing: Weather?, incoming: Weather) -> Weather,
    ): Result<Unit> = when (weather) {
        is Result.Success -> {
            val incoming = weather.data?.toWeather(location.id, System.currentTimeMillis())
            if (incoming != null) {
                cacheMutex.withLock {
                    val existing = readCached(location.id)
                    val merged = combine(existing, incoming)
                    dao.upsert(
                        WeatherCacheEntity(
                            locationId = location.id,
                            payloadJson = json.encodeToString(Weather.serializer(), merged),
                            fetchedAtMillis = merged.fetchedAtMillis,
                        )
                    )
                }
            }
            Result.Success(Unit)
        }
        is Result.Error -> Result.Error(weather.code, weather.message)
    }

    private suspend fun readCached(locationId: Int): Weather? =
        dao.observeForLocation(locationId).firstOrNull()
            ?.let { runCatching { json.decodeFromString(Weather.serializer(), it.payloadJson) }.getOrNull() }
}
