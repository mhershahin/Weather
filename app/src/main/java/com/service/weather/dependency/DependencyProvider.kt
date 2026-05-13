package com.service.weather.dependency

import com.service.daily_presentation.feature_api.DailyFeatureApi
import com.service.radar_presentation.feature_api.RadarFeatureApi
import com.service.weekly_presentation.feature_api.WeeklyFeatureApi

interface DependencyProvider {
    fun dailyFeatureApi(): DailyFeatureApi
    fun weeklyFeatureApi(): WeeklyFeatureApi
    fun radarFeatureApi(): RadarFeatureApi
}
