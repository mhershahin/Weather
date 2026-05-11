package com.service.db.repo.weather

import com.service.entity.Result
import com.service.entity.domain.Location
import com.service.entity.domain.Weather
import com.service.entity.response.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface CachedWeatherRepository {
    fun observeForLocation(locationId: Int): Flow<Weather?>
    suspend fun refresh(location: Location,weather: Result<WeatherResponse>): Result<Unit>
}
