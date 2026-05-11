package com.service.current_domain.di

import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.current_domain.usecase.ObserveCurrentWeatherUseCase
import com.service.current_domain.usecase.ObserveCurrentWeatherUseCaseImpl
import com.service.current_domain.usecase.RefreshCurrentWeatherUseCase
import com.service.current_domain.usecase.RefreshCurrentWeatherUseCaseImpl
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrentDomainModule {

    @Singleton
    @Provides
    fun provideObserveCurrentWeatherUseCase(
        savedRepo: SavedLocationsRepository,
        cachedRepo: CachedWeatherRepository,
    ): ObserveCurrentWeatherUseCase =
        ObserveCurrentWeatherUseCaseImpl(
            savedRepo,
            cachedRepo,
        )

    @Singleton
    @Provides
    fun provideRefreshCurrentWeatherUseCase(
        cachedRepo: CachedWeatherRepository,
        weeklyRepo: WeeklyWeatherRepository,
    ): RefreshCurrentWeatherUseCase =
        RefreshCurrentWeatherUseCaseImpl(
            cachedRepo,
            weeklyRepo,
        )
}