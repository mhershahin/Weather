package com.service.entity.response.weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class HourlyUnits(
    val time: String? = null,
    @SerialName("temperature_2m")
    val temperature: String? = null,
    @SerialName("weather_code")
    val weatherCode: String? = null,
    @SerialName("uv_index")
    val uvIndex: String? = null,
    @SerialName("dew_point_2m")
    val dewPoint: String? = null,
    val visibility: String? = null
)