package com.service.weather.di

import com.service.current_presentation.feature_api.CurrentFeatureApi
import com.service.current_presentation.feature_api.CurrentFeatureApiImpl
import com.service.forecast_presentation.feature_api.ForecastFeatureApi
import com.service.forecast_presentation.feature_api.ForecastFeatureApiImpl
import com.service.rader_presentation.feature_api.RadarFeatureApi
import com.service.rader_presentation.feature_api.RadarFeatureApiImpl
import com.service.weather.dependecy.DependencyProvider
import com.service.weather.dependecy.DependencyProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProviderModule {

    @Provides
    @Singleton
    fun provideCurrentFeatureApi(): CurrentFeatureApi = CurrentFeatureApiImpl()

    @Provides
    @Singleton
    fun provideForecastFeatureApi(): ForecastFeatureApi = ForecastFeatureApiImpl()

    @Provides
    @Singleton
    fun provideRadarFeatureApi(): RadarFeatureApi = RadarFeatureApiImpl()

    @Provides
    @Singleton
    fun provideDependencyProvider(
        currentFeatureApi: CurrentFeatureApi,
        forecastFeatureApi: ForecastFeatureApi,
        radarFeatureApi: RadarFeatureApi
    ): DependencyProvider =
        DependencyProviderImpl(currentFeatureApi, forecastFeatureApi, radarFeatureApi)
}