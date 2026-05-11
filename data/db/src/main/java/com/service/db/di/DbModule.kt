package com.service.db.di

import android.content.Context
import androidx.room.Room
import com.service.db.dao.LocationDao
import com.service.db.dao.WeatherCacheDao
import com.service.db.dao.WeatherDatabase
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.saved.SavedLocationsRepositoryImpl
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.db.repo.weather.CachedWeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideWeatherDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather.db"
        ).build()

    @Singleton
    @Provides
    fun provideLocationDao(
        database: WeatherDatabase
    ): LocationDao = database.locationDao()

    @Singleton
    @Provides
    fun provideWeatherCacheDao(
        database: WeatherDatabase
    ): WeatherCacheDao = database.weatherCacheDao()

    @Singleton
    @Provides
    fun provideSavedLocationsRepository(
        locationDao: LocationDao
    ): SavedLocationsRepository =
        SavedLocationsRepositoryImpl(
            locationDao
        )

    @Singleton
    @Provides
    fun provideCachedWeatherRepository(
        weatherCacheDao: WeatherCacheDao,
    ): CachedWeatherRepository =
        CachedWeatherRepositoryImpl(
            weatherCacheDao
        )
}