package com.service.weather.dependency

import com.service.daily_presentation.feature_api.DailyFeatureApi
import com.service.radar_presentation.feature_api.RadarFeatureApi
import com.service.weekly_presentation.feature_api.WeeklyFeatureApi
import javax.inject.Inject

internal class DependencyProviderImpl @Inject constructor(
    private val dailyFeatureApi: DailyFeatureApi,
    private val weeklyFeatureApi: WeeklyFeatureApi,
    private val radarFeatureApi: RadarFeatureApi
) : DependencyProvider {
    override fun dailyFeatureApi(): DailyFeatureApi = dailyFeatureApi
    override fun weeklyFeatureApi(): WeeklyFeatureApi = weeklyFeatureApi
    override fun radarFeatureApi(): RadarFeatureApi = radarFeatureApi
}
