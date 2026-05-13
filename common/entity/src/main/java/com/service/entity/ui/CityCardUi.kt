package com.service.entity.ui

import com.service.entity.domain.Location

data class CityCardUi(
    val location: Location,
    val tempC: Int?,
    val weatherCode: Int?,
    val isDay: Boolean,
)