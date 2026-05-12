package com.service.daily_domain.di

import com.service.api.repository.dayle.DailyWeatherRepository
import com.service.daily_domain.usecase.ObserveDailyWeatherUseCase
import com.service.daily_domain.usecase.ObserveDailyWeatherUseCaseImpl
import com.service.daily_domain.usecase.refresh.RefreshDailyWeatherUseCase
import com.service.daily_domain.usecase.refresh.RefreshDailyWeatherUseCaseImpl
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DailyDomainModule {

    @Singleton
    @Provides
    fun provideObserveDailyWeatherUseCase(
        savedRepo: SavedLocationsRepository,
        cachedRepo: CachedWeatherRepository,
    ): ObserveDailyWeatherUseCase =
        ObserveDailyWeatherUseCaseImpl(
            savedRepo,
            cachedRepo
        )

    @Singleton
    @Provides
    fun provideRefreshDailyWeatherUseCase(
        cachedRepo: CachedWeatherRepository,
        dailyWeatherRepository: DailyWeatherRepository
    ): RefreshDailyWeatherUseCase =
        RefreshDailyWeatherUseCaseImpl(
            cachedRepo,
            dailyWeatherRepository
        )
}
