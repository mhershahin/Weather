package com.service.api.repository.dayle

import com.service.entity.response.weather.WeatherResponse
import com.service.entity.Result
interface DailyWeatherRepository {
    suspend fun getDailyWeather(latitude: Double,longitude: Double): Result<WeatherResponse>

}