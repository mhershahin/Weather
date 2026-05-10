package com.service.weather.dependecy

import com.service.current_presentation.feature_api.CurrentFeatureApi
import com.service.forecast_presentation.feature_api.ForecastFeatureApi
import com.service.rader_presentation.feature_api.RadarFeatureApi
import javax.inject.Inject

internal class DependencyProviderImpl @Inject constructor(
    private val currentFeatureApi: CurrentFeatureApi,
    private val forecastFeatureApi: ForecastFeatureApi,
    private val radarFeatureApi: RadarFeatureApi
) : DependencyProvider {
    override fun currentFeatureApi(): CurrentFeatureApi = currentFeatureApi
    override fun forecastFeatureApi(): ForecastFeatureApi = forecastFeatureApi
    override fun radarFeatureApi(): RadarFeatureApi = radarFeatureApi
}