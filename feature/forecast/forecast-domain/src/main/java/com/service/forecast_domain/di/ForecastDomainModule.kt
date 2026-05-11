package com.service.forecast_domain.di

import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.forecast_domain.usecase.ObserveForecastUseCase
import com.service.forecast_domain.usecase.ObserveForecastUseCaseImpl
import com.service.forecast_domain.usecase.RefreshForecastUseCase
import com.service.forecast_domain.usecase.RefreshForecastUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ForecastDomainModule {

    @Singleton
    @Provides
    fun provideObserveForecastUseCase(
        savedRepo: SavedLocationsRepository,
        cachedRepo: CachedWeatherRepository,
    ): ObserveForecastUseCase =
        ObserveForecastUseCaseImpl(
            savedRepo,
            cachedRepo,
        )

    @Singleton
    @Provides
    fun provideRefreshForecastUseCase(
        cachedRepo: CachedWeatherRepository,
        weeklyRepo: WeeklyWeatherRepository,
    ): RefreshForecastUseCase =
        RefreshForecastUseCaseImpl(
            cachedRepo,
            weeklyRepo,
        )
}