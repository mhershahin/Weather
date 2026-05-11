package com.service.api.repository.geocoding

import com.service.api.service.geocoding.GeocodingApiService
import com.service.entity.Result
import com.service.entity.response.location.GeocodingResponse
import com.service.utils.analyzeResponse
import com.service.utils.makeApiCall
import javax.inject.Inject

internal class ReverseGeocodingRepositoryImpl @Inject constructor(
    private val geocodingApiService: GeocodingApiService
) : ReverseGeocodingRepository{
    override suspend fun getDailyWeather(
        latitude: Double,
        longitude: Double
    ): Result<GeocodingResponse> {
        return when (val result = makeApiCall({
            analyzeResponse(
                geocodingApiService.reverseGeocoding(latitude, longitude)
            )
        })) {
            is Result.Success -> result
            is Result.Error -> result
        }    }
}