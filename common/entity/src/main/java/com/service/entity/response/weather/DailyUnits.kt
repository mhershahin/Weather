package com.service.entity.response.weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class DailyUnits(
    val time: String? = null,
    @SerialName("temperature_2m_max")
    val temperatureMax: String? = null,
    @SerialName("temperature_2m_min")
    val temperatureMin: String? = null,
    @SerialName("uv_index_max")
    val uvIndexMax: String? = null
)