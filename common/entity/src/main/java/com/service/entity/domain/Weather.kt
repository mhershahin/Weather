package com.service.entity.domain

import kotlinx.serialization.Serializable

@Serializable
data class HourSlot(
    val isoTime: String,
    val tempC: Int,
    val weatherCode: Int,
    val isDay: Boolean,
)

@Serializable
data class DaySlot(
    val isoDate: String,
    val tempMaxC: Int,
    val tempMinC: Int,
    val weatherCode: Int,
    val precipitationProbabilityPct: Int,
)

@Serializable
data class CurrentSnapshot(
    val tempC: Int,
    val weatherCode: Int,
    val isDay: Boolean,
    val humidityPct: Int,
    val dewPointC: Int?,
    val windKmh: Int,
    val windDirectionDeg: Int,
    val visibilityMeters: Double?,
    val uvIndex: Int?,
    val apparentTempC: Int?,
)

@Serializable
data class Weather(
    val locationId: Int,
    val fetchedAtMillis: Long,
    val current: CurrentSnapshot?,
    val hourly: List<HourSlot>,
    val daily: List<DaySlot>,
    val highC: Int?,
    val lowC: Int?,
)
