package com.service.api.repository.weekly

import com.service.entity.response.weather.WeatherResponse
import com.service.entity.Result
interface WeeklyWeatherRepository {
    suspend fun getDailyWeather(latitude: Double,longitude: Double): Result<WeatherResponse>

}