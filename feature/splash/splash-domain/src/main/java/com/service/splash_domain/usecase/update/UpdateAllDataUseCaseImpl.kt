package com.service.splash_domain.usecase.update

import com.service.api.repository.dayle.DailyWeatherRepository
import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.domain.Location
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UpdateAllDataUseCaseImpl @Inject constructor(
    private val savedRepo: SavedLocationsRepository,
    private val weatherRepo: CachedWeatherRepository,
    private val weeklyWeatherRepository: WeeklyWeatherRepository,
    private val dailyWeatherRepository: DailyWeatherRepository,
    private val dispatchers: DispatcherProvider,
) : UpdateAllDataUseCase {

    override suspend fun invoke(location: Location) {
        withContext(dispatchers.io) {
            val weeklyWeather = weeklyWeatherRepository.getWeeklyWeather(location.latitude, location.longitude)
            val dailyWeather = dailyWeatherRepository.getDailyWeather(location.latitude, location.longitude)
            savedRepo.save(location)
            savedRepo.setCurrent(location.id)
            weatherRepo.refreshForecast(location, weeklyWeather)
            weatherRepo.refreshHourly(location, dailyWeather)
        }
    }
}