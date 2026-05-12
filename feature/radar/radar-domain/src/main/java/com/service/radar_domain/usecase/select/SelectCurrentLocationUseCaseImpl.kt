package com.service.radar_domain.usecase.select

import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.domain.Location
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SelectCurrentLocationUseCaseImpl @Inject constructor(
    private val repo: SavedLocationsRepository,
    private val cachedRepo: CachedWeatherRepository,
    private val weeklyRepo: WeeklyWeatherRepository,
    private val dispatchers: DispatcherProvider,
) : SelectCurrentLocationUseCase {

    override suspend fun invoke(location: Location) {
        withContext(dispatchers.io) {
            repo.save(location)
            repo.setCurrent(location.id)
            val response = weeklyRepo.getWeeklyWeather(location.latitude, location.longitude)
            cachedRepo.refreshHourly(location, response)
            cachedRepo.refreshForecast(location, response)
        }
    }
}
