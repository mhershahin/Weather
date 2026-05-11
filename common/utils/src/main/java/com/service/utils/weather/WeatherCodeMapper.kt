package com.service.utils.weather

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.FilterDrama
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector

object WeatherCodeMapper {

    fun icon(code: Int?, isDay: Boolean = true): ImageVector = when (code) {
        0 -> if (isDay) Icons.Filled.WbSunny else Icons.Filled.NightsStay
        1 -> if (isDay) Icons.Filled.WbSunny else Icons.Filled.NightsStay
        2 -> if (isDay) Icons.Filled.WbCloudy else Icons.Filled.Cloud
        3 -> Icons.Filled.Cloud
        45, 48 -> Icons.Filled.BlurOn
        51, 53, 55, 56, 57 -> Icons.Filled.Grain
        61, 63, 65, 66, 67 -> Icons.Filled.Umbrella
        80, 81, 82 -> Icons.Filled.Umbrella
        71, 73, 75, 77, 85, 86 -> Icons.Filled.AcUnit
        95 -> Icons.Filled.Bolt
        96, 99 -> Icons.Filled.Thunderstorm
        else -> Icons.Filled.FilterDrama
    }

    fun label(code: Int?): String = when (code) {
        0 -> "Sunny"
        1 -> "Mostly Sunny"
        2 -> "Partly Cloudy"
        3 -> "Overcast"
        45, 48 -> "Foggy"
        51, 53, 55 -> "Drizzle"
        56, 57 -> "Freezing Drizzle"
        61, 63, 65 -> "Rainy"
        66, 67 -> "Freezing Rain"
        71, 73, 75 -> "Snow"
        77 -> "Snow Grains"
        80, 81, 82 -> "Showers"
        85, 86 -> "Snow Showers"
        95 -> "Thunderstorm"
        96, 99 -> "Thunderstorm w/ Hail"
        else -> "—"
    }

    val Humidity: ImageVector = Icons.Filled.WaterDrop
    val Wind: ImageVector = Icons.Filled.Air
}
