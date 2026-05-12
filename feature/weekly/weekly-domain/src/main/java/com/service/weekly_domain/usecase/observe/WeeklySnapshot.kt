package com.service.weekly_domain.usecase.observe

import com.service.entity.domain.Location
import com.service.entity.domain.Weather

data class WeeklySnapshot(
    val location: Location?,
    val weather: Weather?,
)
