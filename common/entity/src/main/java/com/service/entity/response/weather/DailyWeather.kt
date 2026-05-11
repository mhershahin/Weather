package com.service.entity.response.weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class DailyWeather(
    val time: List<String>? = null,
    @SerialName("temperature_2m_max")
    val temperatureMax: List<Double?>? = null,
    @SerialName("temperature_2m_min")
    val temperatureMin: List<Double?>? = null,
    @SerialName("uv_index_max")
    val uvIndexMax: List<Double?>? = null,
    @SerialName("weather_code")
    val weatherCode: List<Int?>? = null,
    @SerialName("precipitation_probability_max")
    val precipitationProbabilityMax: List<Int?>? = null,
    val sunrise: List<String?>? = null,
    val sunset: List<String?>? = null,
    @SerialName("apparent_temperature_max")
    val apparentTemperatureMax: List<Double?>? = null,
    @SerialName("apparent_temperature_min")
    val apparentTemperatureMin: List<Double?>? = null,
    @SerialName("precipitation_sum")
    val precipitationSum: List<Double?>? = null,
    @SerialName("wind_speed_10m_max")
    val windSpeedMax: List<Double?>? = null
)
