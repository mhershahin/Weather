package com.service.splash_domain.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.service.api.repository.dayle.DailyWeatherRepository
import com.service.api.repository.search.SearchCityRepository
import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.splash_domain.usecase.location.GetCurrentLocationUseCase
import com.service.splash_domain.usecase.location.GetCurrentLocationUseCaseImpl
import com.service.splash_domain.usecase.location.provider.GetLocationProviderUseCase
import com.service.splash_domain.usecase.location.provider.GetLocationProviderUseCaseImpl
import com.service.splash_domain.usecase.permission.HasLocationPermissionUseCase
import com.service.splash_domain.usecase.permission.HasLocationPermissionUseCaseImpl
import com.service.splash_domain.usecase.save.SaveDeviceLocationUseCase
import com.service.splash_domain.usecase.save.SaveDeviceLocationUseCaseImpl
import com.service.splash_domain.usecase.update.UpdateAllDataUseCase
import com.service.splash_domain.usecase.update.UpdateAllDataUseCaseImpl
import com.service.utils.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SplashDomainModule {

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context,
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun provideGetCurrentLocationUseCase(
        savedRepo: SavedLocationsRepository,
        getLocationProviderUseCase: GetLocationProviderUseCase,
        searchCityRepository: SearchCityRepository,
        dispatchers: DispatcherProvider,
    ): GetCurrentLocationUseCase =
        GetCurrentLocationUseCaseImpl(
            savedRepo,
            getLocationProviderUseCase,
            searchCityRepository,
            dispatchers,
        )

    @Singleton
    @Provides
    fun provideGetLocationProviderUseCase(
        @ApplicationContext context: Context,
        dispatchers: DispatcherProvider,
        fusedLocationProviderClient: FusedLocationProviderClient,
    ): GetLocationProviderUseCase =
        GetLocationProviderUseCaseImpl(
            context,
            dispatchers,
            fusedLocationProviderClient,
        )

    @Singleton
    @Provides
    fun provideHasLocationPermissionUseCase(
        @ApplicationContext context: Context,
        dispatchers: DispatcherProvider,
    ): HasLocationPermissionUseCase =
        HasLocationPermissionUseCaseImpl(
            context,
            dispatchers,
        )

    @Singleton
    @Provides
    fun provideSaveDeviceLocationUseCase(
        savedRepo: SavedLocationsRepository,
        getLocationProviderUseCase: GetLocationProviderUseCase,
        searchCityRepository: SearchCityRepository,
        dispatchers: DispatcherProvider,
    ): SaveDeviceLocationUseCase =
        SaveDeviceLocationUseCaseImpl(
            savedRepo,
            getLocationProviderUseCase,
            searchCityRepository,
            dispatchers,
        )

    @Singleton
    @Provides
    fun provideUpdateAllDataUseCase(
        savedRepo: SavedLocationsRepository,
        weatherRepo: CachedWeatherRepository,
        weeklyWeatherRepository: WeeklyWeatherRepository,
        dailyWeatherRepository: DailyWeatherRepository,
        dispatchers: DispatcherProvider,
    ): UpdateAllDataUseCase =
        UpdateAllDataUseCaseImpl(
            savedRepo,
            weatherRepo,
            weeklyWeatherRepository,
            dailyWeatherRepository,
            dispatchers,
        )
}