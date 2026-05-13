package com.service.daily_domain.di

import com.service.daily_domain.usecase.observe.ObserveDailyWeatherUseCase
import com.service.daily_domain.usecase.observe.ObserveDailyWeatherUseCaseImpl
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.utils.dispatcher.DispatcherProvider
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
        dispatchers: DispatcherProvider,
    ): ObserveDailyWeatherUseCase =
        ObserveDailyWeatherUseCaseImpl(
            savedRepo,
            cachedRepo,
            dispatchers,
        )
}
