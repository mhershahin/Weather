package com.service.weekly_domain.usecase

import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.Result
import com.service.entity.domain.Location
import javax.inject.Inject

interface RefreshWeeklyUseCase {
    suspend operator fun invoke(location: Location): Result<Unit>
}

class RefreshWeeklyUseCaseImpl @Inject constructor(
    private val cachedRepo: CachedWeatherRepository,
    private val weeklyRepo: WeeklyWeatherRepository
) : RefreshWeeklyUseCase {

    override suspend fun invoke(location: Location): Result<Unit> =
        cachedRepo.refreshForecast(
            location,
            weeklyRepo.getWeeklyWeather(location.latitude, location.longitude),
        )
}
