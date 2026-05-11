package com.service.api.repository.weekly

import com.service.entity.Result
import com.service.entity.response.weather.WeatherResponse

interface WeeklyWeatherRepository {
    suspend fun getWeeklyWeather(latitude: Double, longitude: Double): Result<WeatherResponse>

}