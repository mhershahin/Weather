package com.service.weekly_domain.usecase

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

data class WeeklySnapshot(
    val location: Location?,
    val weather: Weather?,
)

interface ObserveWeeklyUseCase {
    operator fun invoke(): Flow<WeeklySnapshot>
}

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveWeeklyUseCaseImpl @Inject constructor(
    private val savedRepo: SavedLocationsRepository,
    private val cachedRepo: CachedWeatherRepository,
) : ObserveWeeklyUseCase {

    override fun invoke(): Flow<WeeklySnapshot> =
        savedRepo.observeCurrent().flatMapLatest { loc ->
            if (loc == null) flowOf(WeeklySnapshot(null, null))
            else combine(flowOf(loc), cachedRepo.observeForLocation(loc.id)) { l, w ->
                WeeklySnapshot(l, w)
            }
        }
}
