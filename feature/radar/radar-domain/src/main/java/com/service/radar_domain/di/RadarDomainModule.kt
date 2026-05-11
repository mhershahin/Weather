package com.service.radar_domain.di

import com.service.api.repository.multi.MultiLocationRepository
import com.service.api.repository.search.SearchCityRepository
import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.radar_domain.usecase.AddLocationUseCase
import com.service.radar_domain.usecase.AddLocationUseCaseImpl
import com.service.radar_domain.usecase.FetchCityCardsUseCase
import com.service.radar_domain.usecase.FetchCityCardsUseCaseImpl
import com.service.radar_domain.usecase.ObserveSavedLocationsUseCase
import com.service.radar_domain.usecase.ObserveSavedLocationsUseCaseImpl
import com.service.radar_domain.usecase.RemoveLocationUseCase
import com.service.radar_domain.usecase.RemoveLocationUseCaseImpl
import com.service.radar_domain.usecase.SearchLocationsUseCase
import com.service.radar_domain.usecase.SearchLocationsUseCaseImpl
import com.service.radar_domain.usecase.SelectCurrentLocationUseCase
import com.service.radar_domain.usecase.SelectCurrentLocationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RadarDomainModule {

    @Singleton
    @Provides
    fun provideObserveSavedLocationsUseCase(
        repo: SavedLocationsRepository,
    ): ObserveSavedLocationsUseCase =
        ObserveSavedLocationsUseCaseImpl(
            repo,
        )

    @Singleton
    @Provides
    fun provideSearchLocationsUseCase(
        searchRepo: SearchCityRepository,
    ): SearchLocationsUseCase =
        SearchLocationsUseCaseImpl(
            searchRepo,
        )

    @Singleton
    @Provides
    fun provideAddLocationUseCase(
        repo: SavedLocationsRepository,
    ): AddLocationUseCase =
        AddLocationUseCaseImpl(
            repo,
        )

    @Singleton
    @Provides
    fun provideRemoveLocationUseCase(
        repo: SavedLocationsRepository,
    ): RemoveLocationUseCase =
        RemoveLocationUseCaseImpl(
            repo,
        )

    @Singleton
    @Provides
    fun provideSelectCurrentLocationUseCase(
        repo: SavedLocationsRepository,
        cachedRepo: CachedWeatherRepository,
        weeklyRepo: WeeklyWeatherRepository,
    ): SelectCurrentLocationUseCase =
        SelectCurrentLocationUseCaseImpl(
            repo,
            cachedRepo,
            weeklyRepo,
        )

    @Singleton
    @Provides
    fun provideFetchCityCardsUseCase(
        multiRepo: MultiLocationRepository,
    ): FetchCityCardsUseCase =
        FetchCityCardsUseCaseImpl(
            multiRepo,
        )
}