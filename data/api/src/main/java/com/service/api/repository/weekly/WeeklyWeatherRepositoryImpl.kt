package com.service.api.repository.weekly

import com.service.api.service.weather.WeatherApiService
import com.service.entity.Result
import com.service.entity.response.weather.WeatherResponse
import com.service.utils.analyzeResponse
import com.service.utils.makeApiCall
import javax.inject.Inject

internal class WeeklyWeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeeklyWeatherRepository{
    override suspend fun getWeeklyWeather(
        latitude: Double,
        longitude: Double
    ): Result<WeatherResponse> {
        return when (val result = makeApiCall({
            analyzeResponse(
                weatherApiService.getWeeklyWeather(latitude, longitude)
            )
        })) {
            is Result.Success -> result
            is Result.Error -> result
        }    }
}