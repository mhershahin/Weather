package com.service.api.repository.multi

import com.service.api.service.weather.WeatherApiService
import com.service.entity.response.weather.WeatherResponse
import javax.inject.Inject
import com.service.entity.Result
import com.service.utils.analyzeResponse
import com.service.utils.makeApiCall

internal class MultiLocationRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : MultiLocationRepository {
    override suspend fun getMultiLocationCurrentWeather(
        latitudes: String,
        longitudes: String
    ): Result<List<WeatherResponse>> {
        return when (val result = makeApiCall({
            analyzeResponse(
                weatherApiService.getMultiLocationCurrentWeather(latitudes, longitudes)
            )
        })) {
            is Result.Success -> result
            is Result.Error -> result
        }
    }
}