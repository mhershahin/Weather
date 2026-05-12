package com.service.radar_domain.di

import com.service.api.repository.multi.MultiLocationRepository
import com.service.api.repository.search.SearchCityRepository
import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.radar_domain.usecase.add.AddLocationUseCase
import com.service.radar_domain.usecase.add.AddLocationUseCaseImpl
import com.service.radar_domain.usecase.fetch.FetchCityCardsUseCase
import com.service.radar_domain.usecase.fetch.FetchCityCardsUseCaseImpl
import com.service.radar_domain.usecase.observe.ObserveSavedLocationsUseCase
import com.service.radar_domain.usecase.observe.ObserveSavedLocationsUseCaseImpl
import com.service.radar_domain.usecase.remove.RemoveLocationUseCase
import com.service.radar_domain.usecase.remove.RemoveLocationUseCaseImpl
import com.service.radar_domain.usecase.search.SearchLocationsUseCase
import com.service.radar_domain.usecase.search.SearchLocationsUseCaseImpl
import com.service.radar_domain.usecase.select.SelectCurrentLocationUseCase
import com.service.radar_domain.usecase.select.SelectCurrentLocationUseCaseImpl
import com.service.utils.dispatcher.DispatcherProvider
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
        dispatchers: DispatcherProvider,
    ): ObserveSavedLocationsUseCase =
        ObserveSavedLocationsUseCaseImpl(
            repo,
            dispatchers,
        )

    @Singleton
    @Provides
    fun provideSearchLocationsUseCase(
        searchRepo: SearchCityRepository,
        dispatchers: DispatcherProvider,
    ): SearchLocationsUseCase =
        SearchLocationsUseCaseImpl(
            searchRepo,
            dispatchers,
        )

    @Singleton
    @Provides
    fun provideAddLocationUseCase(
        repo: SavedLocationsRepository,
        dispatchers: DispatcherProvider,
    ): AddLocationUseCase =
        AddLocationUseCaseImpl(
            repo,
            dispatchers,
        )

    @Singleton
    @Provides
    fun provideRemoveLocationUseCase(
        repo: SavedLocationsRepository,
        dispatchers: DispatcherProvider,
    ): RemoveLocationUseCase =
        RemoveLocationUseCaseImpl(
            repo,
            dispatchers,
        )

    @Singleton
    @Provides
    fun provideSelectCurrentLocationUseCase(
        repo: SavedLocationsRepository,
        cachedRepo: CachedWeatherRepository,
        weeklyRepo: WeeklyWeatherRepository,
        dispatchers: DispatcherProvider,
    ): SelectCurrentLocationUseCase =
        SelectCurrentLocationUseCaseImpl(
            repo,
            cachedRepo,
            weeklyRepo,
            dispatchers,
        )

    @Singleton
    @Provides
    fun provideFetchCityCardsUseCase(
        multiRepo: MultiLocationRepository,
        dispatchers: DispatcherProvider,
    ): FetchCityCardsUseCase =
        FetchCityCardsUseCaseImpl(
            multiRepo,
            dispatchers,
        )
}
