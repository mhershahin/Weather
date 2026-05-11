package com.service.forecast_domain.usecase

import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.domain.Location
import com.service.entity.domain.Weather

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

data class ForecastSnapshot(
    val location: Location?,
    val weather: Weather?,
)

interface ObserveForecastUseCase {
    operator fun invoke(): Flow<ForecastSnapshot>
}

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveForecastUseCaseImpl @Inject constructor(
    private val savedRepo: SavedLocationsRepository,
    private val cachedRepo: CachedWeatherRepository,
) : ObserveForecastUseCase {

    override fun invoke(): Flow<ForecastSnapshot> =
        savedRepo.observeCurrent().flatMapLatest { loc ->
            if (loc == null) flowOf(ForecastSnapshot(null, null))
            else combine(flowOf(loc), cachedRepo.observeForLocation(loc.id)) { l, w ->
                ForecastSnapshot(l, w)
            }
        }
}
