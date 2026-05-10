package com.service.entity.response.location
import kotlinx.serialization.Serializable
@Serializable
data class GeocodingResponse(
    val results: List<GeoLocation>? = null
)