package com.service.entity.domain

data class Location(
    val id: Int,
    val name: String,
    val country: String,
    val region: String,
    val latitude: Double,
    val longitude: Double,
    val isCurrent: Boolean = false,
    val kind: LocationKind = LocationKind.SAVED,
) {


    val displayName: String
        get() = if (region.isNotEmpty() && region != name) "$name, $region" else name

    companion object {
        fun getDefault(): Location  = Location(
            id = 2643743,
            name = "London",
            country = "United Kingdom",
            region = "England",
            latitude = 51.5074,
            longitude = -0.1278,
        )
    }
}

enum class LocationKind { SAVED, GPS }
