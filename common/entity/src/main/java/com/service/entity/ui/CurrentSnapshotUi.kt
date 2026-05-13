package com.service.entity.ui

import com.service.entity.domain.Location
import com.service.entity.domain.Weather

data class CurrentSnapshotUi(
    val location: Location?,
    val weather: Weather?,
    val gpsLocationId: Int? = null,
)