package com.service.weather.di

import com.service.daily_presentation.feature_api.DailyFeatureApi
import com.service.daily_presentation.feature_api.DailyFeatureApiImpl
import com.service.radar_presentation.feature_api.RadarFeatureApi
import com.service.radar_presentation.feature_api.RadarFeatureApiImpl
import com.service.weather.dependency.DependencyProvider
import com.service.weather.dependency.DependencyProviderImpl
import com.service.weekly_presentation.feature_api.WeeklyFeatureApi
import com.service.weekly_presentation.feature_api.WeeklyFeatureApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProviderModule {

    @Provides
    @Singleton
    fun provideDailyFeatureApi(): DailyFeatureApi = DailyFeatureApiImpl()

    @Provides
    @Singleton
    fun provideWeeklyFeatureApi(): WeeklyFeatureApi = WeeklyFeatureApiImpl()

    @Provides
    @Singleton
    fun provideRadarFeatureApi(): RadarFeatureApi = RadarFeatureApiImpl()

    @Provides
    @Singleton
    fun provideDependencyProvider(
        dailyFeatureApi: DailyFeatureApi,
        weeklyFeatureApi: WeeklyFeatureApi,
        radarFeatureApi: RadarFeatureApi
    ): DependencyProvider =
        DependencyProviderImpl(dailyFeatureApi, weeklyFeatureApi, radarFeatureApi)
}
