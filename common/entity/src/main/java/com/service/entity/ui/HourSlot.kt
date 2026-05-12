package com.service.entity.ui

data class HourSlot(
    val label: String,
    val tempC: Int,
    val weatherCode: Int,
    val isDay: Boolean,
)