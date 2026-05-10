package com.service.entity.response.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather(
    val time: String? = null,
    val interval: Int? = null,
    @SerialName("temperature_2m")
    val temperature: Double? = null,
    @SerialName("relative_humidity_2m")
    val relativeHumidity: Int? = null,
    @SerialName("apparent_temperature")
    val apparentTemperature: Double? = null,
    @SerialName("is_day")
    val isDay: Int? = null,
    val precipitation: Double? = null,
    @SerialName("weather_code")
    val weatherCode: Int? = null,
    @SerialName("cloud_cover")
    val cloudCover: Int? = null,
    @SerialName("wind_speed_10m")
    val windSpeed: Double? = null,
    @SerialName("wind_direction_10m")
    val windDirection: Int? = null,
    @SerialName("wind_gusts_10m")
    val windGusts: Double? = null
)