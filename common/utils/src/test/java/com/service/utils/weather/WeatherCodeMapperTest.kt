package com.service.utils.weather

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterDrama
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherCodeMapperTest {

    @Test
    fun `clear sky day uses sunny icon`() {
        assertEquals(Icons.Filled.WbSunny, WeatherCodeMapper.icon(code = 0, isDay = true))
    }

    @Test
    fun `clear sky night uses moon icon`() {
        assertEquals(Icons.Filled.NightsStay, WeatherCodeMapper.icon(code = 0, isDay = false))
    }

    @Test
    fun `unknown code falls back to filter drama icon`() {
        assertEquals(Icons.Filled.FilterDrama, WeatherCodeMapper.icon(code = null))
        assertEquals(Icons.Filled.FilterDrama, WeatherCodeMapper.icon(code = 9999))
    }

    @Test
    fun `rain codes share rainy label`() {
        assertEquals("Rainy", WeatherCodeMapper.label(61))
        assertEquals("Rainy", WeatherCodeMapper.label(63))
        assertEquals("Rainy", WeatherCodeMapper.label(65))
    }

    @Test
    fun `thunderstorm code labelled`() {
        assertEquals("Thunderstorm", WeatherCodeMapper.label(95))
    }

    @Test
    fun `null code labelled em dash`() {
        assertEquals("—", WeatherCodeMapper.label(null))
    }
}
