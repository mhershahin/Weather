package com.service.entity.ui

import com.service.entity.domain.Location
import com.service.entity.domain.Weather

data class CurrentSnapshot(
    val location: Location?,
    val weather: Weather?,
)