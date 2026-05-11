package com.service.api.repository.geocoding

import com.service.entity.Result
import com.service.entity.response.location.GeocodingResponse

interface ReverseGeocodingRepository {
    suspend fun getDailyWeather(latitude: Double,longitude: Double): Result<GeocodingResponse>

}