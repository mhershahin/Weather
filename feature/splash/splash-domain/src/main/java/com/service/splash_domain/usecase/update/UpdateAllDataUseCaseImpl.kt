package com.service.splash_domain.usecase.update

import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.domain.Location
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UpdateAllDataUseCaseImpl @Inject constructor(
    private val savedRepo: SavedLocationsRepository,
    private val weatherRepo: CachedWeatherRepository,
    private val weeklyRepo: WeeklyWeatherRepository,
    private val dispatchers: DispatcherProvider,
) : UpdateAllDataUseCase {

    override suspend fun invoke(location: Location?) {
        withContext(dispatchers.io) {
            val target = location ?: savedRepo.observeCurrent().firstOrNull()
            target?.let { weatherRepo.refresh(it,weeklyRepo.getWeeklyWeather(it.latitude,it.longitude)) }
        }
    }
}