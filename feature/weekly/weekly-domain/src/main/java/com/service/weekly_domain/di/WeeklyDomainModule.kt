package com.service.weekly_domain.di

import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.weekly_domain.usecase.ObserveWeeklyUseCase
import com.service.weekly_domain.usecase.ObserveWeeklyUseCaseImpl
import com.service.weekly_domain.usecase.RefreshWeeklyUseCase
import com.service.weekly_domain.usecase.RefreshWeeklyUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeeklyDomainModule {

    @Singleton
    @Provides
    fun provideObserveWeeklyUseCase(
        savedRepo: SavedLocationsRepository,
        cachedRepo: CachedWeatherRepository,
    ): ObserveWeeklyUseCase =
        ObserveWeeklyUseCaseImpl(
            savedRepo,
            cachedRepo,
        )

    @Singleton
    @Provides
    fun provideRefreshWeeklyUseCase(
        cachedRepo: CachedWeatherRepository,
        weeklyRepo: WeeklyWeatherRepository,
    ): RefreshWeeklyUseCase =
        RefreshWeeklyUseCaseImpl(
            cachedRepo,
            weeklyRepo,
        )
}
