package com.service.forecast_domain.usecase

import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.Result
import com.service.entity.domain.Location
import javax.inject.Inject

interface RefreshForecastUseCase {
    suspend operator fun invoke(location: Location): Result<Unit>
}

class RefreshForecastUseCaseImpl @Inject constructor(
    private val cachedRepo: CachedWeatherRepository,
    private val weeklyRepo: WeeklyWeatherRepository
) : RefreshForecastUseCase {

    override suspend fun invoke(location: Location): Result<Unit> = cachedRepo.refresh(location,weeklyRepo.getWeeklyWeather(location.latitude,location.longitude))
}
