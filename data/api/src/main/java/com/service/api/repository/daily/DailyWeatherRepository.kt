package com.service.api.repository.daily

import com.service.entity.Result
import com.service.entity.response.weather.WeatherResponse

interface DailyWeatherRepository {
    suspend fun getDailyWeather(latitude: Double,longitude: Double): Result<WeatherResponse>

}