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
}

enum class LocationKind { SAVED, GPS }
