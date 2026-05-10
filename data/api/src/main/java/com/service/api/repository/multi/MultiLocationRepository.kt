package com.service.api.repository.multi

import com.service.entity.response.weather.WeatherResponse
import com.service.entity.Result

interface MultiLocationRepository {
    suspend fun getMultiLocationCurrentWeather(
        latitudes: String,
        longitudes: String
    ): Result<List<WeatherResponse>>
}