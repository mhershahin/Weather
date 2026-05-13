package com.service.api.di

import com.service.api.BuildConfig
import com.service.api.repository.daily.DailyWeatherRepository
import com.service.api.repository.daily.DailyWeatherRepositoryImpl
import com.service.api.repository.multi.MultiLocationRepository
import com.service.api.repository.multi.MultiLocationRepositoryImpl
import com.service.api.repository.search.SearchCityRepository
import com.service.api.repository.search.SearchCityRepositoryImpl
import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.api.repository.weekly.WeeklyWeatherRepositoryImpl
import com.service.api.service.geocoding.GeocodingApiService
import com.service.api.service.geocoding.GeocodingConstants
import com.service.api.service.weather.RequestConstants.Companion.BASE_URL
import com.service.api.service.weather.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiRepositoryModule {

    @Singleton
    @Provides
    @Named("WeatherJson")
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    @Singleton
    @Provides
    @Named("WeatherOkHttpClient")
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Singleton
    @Provides
    @Named("WeatherRetrofit")
    fun provideWeatherRetrofit(
        @Named("WeatherOkHttpClient") okHttpClient: OkHttpClient,
        @Named("WeatherJson") json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Singleton
    @Provides
    @Named("GeocodingRetrofit")
    fun provideGeocodingRetrofit(
        @Named("WeatherOkHttpClient") okHttpClient: OkHttpClient,
        @Named("WeatherJson") json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(GeocodingConstants.GEOCODING_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Singleton
    @Provides
    fun provideWeatherApiService(
        @Named("WeatherRetrofit") retrofit: Retrofit
    ): WeatherApiService =
        retrofit.create(WeatherApiService::class.java)

    @Singleton
    @Provides
    fun provideGeocodingApiService(
        @Named("GeocodingRetrofit") retrofit: Retrofit
    ): GeocodingApiService =
        retrofit.create(GeocodingApiService::class.java)

    @Singleton
    @Provides
    fun provideDailyWeatherRepository(
        weatherApiService: WeatherApiService
    ): DailyWeatherRepository =
        DailyWeatherRepositoryImpl(
            weatherApiService
        )

    @Singleton
    @Provides
    fun provideWeeklyWeatherRepository(
        weatherApiService: WeatherApiService
    ): WeeklyWeatherRepository =
        WeeklyWeatherRepositoryImpl(
            weatherApiService
        )

    @Singleton
    @Provides
    fun provideSearchCityRepository(
        geocodingApiService: GeocodingApiService
    ): SearchCityRepository =
        SearchCityRepositoryImpl(
            geocodingApiService
        )



    @Singleton
    @Provides
    fun provideMultiLocationRepository(
        weatherApiService: WeatherApiService
    ): MultiLocationRepository =
        MultiLocationRepositoryImpl(
            weatherApiService
        )

}