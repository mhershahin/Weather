package com.service.api.service.weather

import com.service.api.service.weather.RequestConstants.Companion.CURRENT_PARAMS
import com.service.api.service.weather.RequestConstants.Companion.DAILY_PARAMS
import com.service.api.service.weather.RequestConstants.Companion.FORECAST
import com.service.api.service.weather.RequestConstants.Companion.WEEKLY_DAILY_PARAMS
import com.service.api.service.weather.RequestConstants.Companion.WEEKLY_HOURLY_PARAMS
import com.service.api.service.weather.RequestConstants.Companion.FORECAST_DAYS_DAILY
import com.service.api.service.weather.RequestConstants.Companion.HOURLY_PARAMS
import com.service.api.service.weather.RequestConstants.Companion.FORECAST_DAYS_WEEKLY
import com.service.api.service.weather.RequestConstants.Companion.MULTI_LOCATION_PARAMS
import com.service.api.service.weather.RequestConstants.Companion.TEMP_UNIT
import com.service.api.service.weather.RequestConstants.Companion.TIME_ZONE
import com.service.api.service.weather.RequestConstants.Companion.WIND_SPEED_UNIT
import com.service.entity.response.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET(FORECAST)
    suspend fun getDailyWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = CURRENT_PARAMS,
        @Query("hourly") hourly: String = HOURLY_PARAMS,
        @Query("daily") daily: String = DAILY_PARAMS,
        @Query("temperature_unit") temperatureUnit: String = TEMP_UNIT,
        @Query("wind_speed_unit") windSpeedUnit: String = WIND_SPEED_UNIT,
        @Query("timezone") timezone: String = TIME_ZONE,
        @Query("forecast_days") forecastDays: Int = FORECAST_DAYS_DAILY
    ): Response<WeatherResponse>

    @GET(FORECAST)
    suspend fun getWeeklyWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String = WEEKLY_DAILY_PARAMS,
        @Query("hourly") hourly: String = WEEKLY_HOURLY_PARAMS,
        @Query("temperature_unit") temperatureUnit: String = TEMP_UNIT,
        @Query("wind_speed_unit") windSpeedUnit: String = WIND_SPEED_UNIT,
        @Query("timezone") timezone: String = TIME_ZONE,
        @Query("forecast_days") forecastDays: Int = FORECAST_DAYS_WEEKLY
    ): Response<WeatherResponse>
    @GET(FORECAST)
    suspend fun getMultiLocationCurrentWeather(
        @Query("latitude") latitudes: String,
        @Query("longitude") longitudes: String,
        @Query("current") current: String = MULTI_LOCATION_PARAMS,
        @Query("temperature_unit") temperatureUnit: String = TEMP_UNIT,
        @Query("wind_speed_unit") windSpeedUnit: String = WIND_SPEED_UNIT,
        @Query("timezone") timezone: String = TIME_ZONE
    ): Response<List<WeatherResponse>>
}