package com.service.api.repository.weekly

import com.service.api.service.weather.WeatherApiService
import com.service.entity.response.weather.WeatherResponse
import javax.inject.Inject
import com.service.entity.Result
import com.service.utils.analyzeResponse
import com.service.utils.makeApiCall

internal class WeeklyWeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeeklyWeatherRepository{
    override suspend fun getDailyWeather(
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