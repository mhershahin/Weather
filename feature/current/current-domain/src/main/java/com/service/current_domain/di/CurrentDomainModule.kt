package com.service.current_domain.di

import com.service.api.repository.dayle.DailyWeatherRepository
import com.service.current_domain.usecase.ObserveCurrentWeatherUseCase
import com.service.current_domain.usecase.ObserveCurrentWeatherUseCaseImpl
import com.service.current_domain.usecase.refresh.RefreshCurrentWeatherUseCase
import com.service.current_domain.usecase.refresh.RefreshCurrentWeatherUseCaseImpl
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
            cachedRepo
        )

    @Singleton
    @Provides
    fun provideRefreshCurrentWeatherUseCase(
        cachedRepo: CachedWeatherRepository,
        dailyWeatherRepository: DailyWeatherRepository
    ): RefreshCurrentWeatherUseCase =
        RefreshCurrentWeatherUseCaseImpl(
            cachedRepo,
            dailyWeatherRepository
        )
}