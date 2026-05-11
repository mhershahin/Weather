package com.service.current_domain.usecase

import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.Result
import com.service.entity.domain.Location
import javax.inject.Inject

interface RefreshCurrentWeatherUseCase {
    suspend operator fun invoke(location: Location): Result<Unit>
}

class RefreshCurrentWeatherUseCaseImpl @Inject constructor(
    private val cachedRepo: CachedWeatherRepository,
    private val weeklyRepo: WeeklyWeatherRepository
) : RefreshCurrentWeatherUseCase {

    override suspend fun invoke(location: Location): Result<Unit> = cachedRepo.refresh(location,weeklyRepo.getWeeklyWeather(location.latitude,location.longitude))
}
