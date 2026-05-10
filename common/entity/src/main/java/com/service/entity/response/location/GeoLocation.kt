package com.service.entity.response.location

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoLocation(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val elevation: Double? = null,
    @SerialName("feature_code")
    val featureCode: String? = null,
    @SerialName("country_code")
    val countryCode: String? = null,
    val timezone: String? = null,
    val population: Int? = null,
    val postcodes: List<String>? = null,
    val country: String? = null,
    @SerialName("country_id")
    val countryId: Int? = null,
    val admin1: String? = null,
    @SerialName("admin1_id")
    val admin1Id: Int? = null,
    val admin2: String? = null,
    @SerialName("admin2_id")
    val admin2Id: Int? = null,
    val admin3: String? = null,
    @SerialName("admin3_id")
    val admin3Id: Int? = null,
    val admin4: String? = null,
    @SerialName("admin4_id")
    val admin4Id: Int? = null
)