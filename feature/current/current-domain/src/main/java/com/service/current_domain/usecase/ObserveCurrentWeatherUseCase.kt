package com.service.current_domain.usecase

import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.ui.CurrentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject



interface ObserveCurrentWeatherUseCase {
    operator fun invoke(): Flow<CurrentSnapshot>
}

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveCurrentWeatherUseCaseImpl @Inject constructor(
    private val savedRepo: SavedLocationsRepository,
    private val cachedRepo: CachedWeatherRepository,
) : ObserveCurrentWeatherUseCase {

    override fun invoke(): Flow<CurrentSnapshot> =
        savedRepo.observeCurrent().flatMapLatest { loc ->
            if (loc == null) flowOf(CurrentSnapshot(null, null))
            else combine(flowOf(loc), cachedRepo.observeForLocation(loc.id)) { l, w ->
                CurrentSnapshot(l, w)
            }
        }.flowOn(Dispatchers.IO)
}
