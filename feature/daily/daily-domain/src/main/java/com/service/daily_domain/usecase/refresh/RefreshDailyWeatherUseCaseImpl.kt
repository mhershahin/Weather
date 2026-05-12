package com.service.daily_domain.usecase.refresh

import com.service.api.repository.dayle.DailyWeatherRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.Result
import com.service.entity.domain.Location
import javax.inject.Inject

internal class RefreshDailyWeatherUseCaseImpl @Inject constructor(
    private val cachedRepo: CachedWeatherRepository,
    private val dailyWeatherRepository: DailyWeatherRepository
) : RefreshDailyWeatherUseCase {

    override suspend fun invoke(location: Location): Result<Unit> =
        cachedRepo.refreshHourly(
            location,
            dailyWeatherRepository.getDailyWeather(location.latitude, location.longitude),
        )
}
