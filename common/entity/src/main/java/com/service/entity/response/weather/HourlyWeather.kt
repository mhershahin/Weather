package com.service.entity.response.weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class HourlyWeather(
    val time: List<String>? = null,
    @SerialName("temperature_2m")
    val temperature: List<Double?>? = null,
    @SerialName("weather_code")
    val weatherCode: List<Int?>? = null,
    @SerialName("uv_index")
    val uvIndex: List<Double?>? = null,
    @SerialName("dew_point_2m")
    val dewPoint: List<Double?>? = null,
    val visibility: List<Double?>? = null,
    @SerialName("precipitation_probability")
    val precipitationProbability: List<Int?>? = null
)
