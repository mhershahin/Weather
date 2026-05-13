package com.service.radar_domain.usecase.select

import com.service.api.repository.daily.DailyWeatherRepository
import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.domain.Location
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UpdateWeatherDataUseCaseImpl @Inject constructor(
    private val repo: SavedLocationsRepository,
    private val cachedRepo: CachedWeatherRepository,
    private val weeklyRepo: WeeklyWeatherRepository,
    private val dailyRepo: DailyWeatherRepository,
    private val dispatchers: DispatcherProvider,
) : UpdateWeatherDataUseCase {

    override suspend fun invoke(location: Location) {
        withContext(dispatchers.io) {
            repo.save(location)
            val weekly = weeklyRepo.getWeeklyWeather(location.latitude, location.longitude)
            val daily = dailyRepo.getDailyWeather(location.latitude, location.longitude)
            cachedRepo.refreshHourly(location, daily)
            cachedRepo.refreshForecast(location, weekly)
            repo.setCurrent(location.id)
        }
    }
}
