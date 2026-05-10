package com.service.entity.response.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val elevation: Double? = null,
    @SerialName("generationtime_ms")
    val generationTimeMs: Double? = null,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int? = null,
    val timezone: String? = null,
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String? = null,
    val current: CurrentWeather? = null,
    @SerialName("current_units")
    val currentUnits: CurrentUnits? = null,
    val hourly: HourlyWeather? = null,
    @SerialName("hourly_units")
    val hourlyUnits: HourlyUnits? = null,
    val daily: DailyWeather? = null,
    @SerialName("daily_units")
    val dailyUnits: DailyUnits? = null
)











