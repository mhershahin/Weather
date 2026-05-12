package com.service.weekly_domain.di

import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.utils.dispatcher.DispatcherProvider
import com.service.weekly_domain.usecase.observe.ObserveWeeklyUseCase
import com.service.weekly_domain.usecase.observe.ObserveWeeklyUseCaseImpl
import com.service.weekly_domain.usecase.refresh.RefreshWeeklyUseCase
import com.service.weekly_domain.usecase.refresh.RefreshWeeklyUseCaseImpl
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
        dispatchers: DispatcherProvider,
    ): ObserveWeeklyUseCase =
        ObserveWeeklyUseCaseImpl(
            savedRepo,
            cachedRepo,
            dispatchers,
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
