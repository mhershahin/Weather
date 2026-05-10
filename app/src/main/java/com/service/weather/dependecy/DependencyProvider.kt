package com.service.weather.dependecy

import com.service.current_presentation.feature_api.CurrentFeatureApi
import com.service.forecast_presentation.feature_api.ForecastFeatureApi
import com.service.rader_presentation.feature_api.RadarFeatureApi

interface DependencyProvider {
    fun currentFeatureApi(): CurrentFeatureApi
    fun forecastFeatureApi(): ForecastFeatureApi
    fun radarFeatureApi(): RadarFeatureApi
}