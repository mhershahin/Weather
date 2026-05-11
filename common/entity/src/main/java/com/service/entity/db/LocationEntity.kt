package com.service.entity.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.service.entity.domain.Location
import com.service.entity.domain.LocationKind

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val country: String,
    val region: String,
    val latitude: Double,
    val longitude: Double,
    val isCurrent: Boolean,
    val kind: String,
) {
    fun toDomain(): Location = Location(
        id = id,
        name = name,
        country = country,
        region = region,
        latitude = latitude,
        longitude = longitude,
        isCurrent = isCurrent,
        kind = runCatching { LocationKind.valueOf(kind) }.getOrElse { LocationKind.SAVED },
    )

    companion object {
        fun fromDomain(location: Location): LocationEntity = LocationEntity(
            id = location.id,
            name = location.name,
            country = location.country,
            region = location.region,
            latitude = location.latitude,
            longitude = location.longitude,
            isCurrent = location.isCurrent,
            kind = location.kind.name,
        )
    }
}
