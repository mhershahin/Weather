package com.service.entity.response.weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class CurrentUnits(
    val time: String? = null,
    val interval: String? = null,
    @SerialName("temperature_2m")
    val temperature: String? = null,
    @SerialName("relative_humidity_2m")
    val relativeHumidity: String? = null,
    @SerialName("apparent_temperature")
    val apparentTemperature: String? = null,
    @SerialName("is_day")
    val isDay: String? = null,
    val precipitation: String? = null,
    @SerialName("weather_code")
    val weatherCode: String? = null,
    @SerialName("cloud_cover")
    val cloudCover: String? = null,
    @SerialName("wind_speed_10m")
    val windSpeed: String? = null,
    @SerialName("wind_direction_10m")
    val windDirection: String? = null,
    @SerialName("wind_gusts_10m")
    val windGusts: String? = null
)