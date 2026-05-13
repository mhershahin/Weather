package com.service.daily_domain.usecase.observe

import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.ui.CurrentSnapshotUi
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class ObserveDailyWeatherUseCaseImpl @Inject constructor(
    private val savedRepo: SavedLocationsRepository,
    private val cachedRepo: CachedWeatherRepository,
    private val dispatchers: DispatcherProvider,
) : ObserveDailyWeatherUseCase {

    override fun invoke(): Flow<CurrentSnapshotUi> =
        combine(savedRepo.observeCurrent(), savedRepo.observeGps()) { active, gps ->
            active to gps?.id
        }.flatMapLatest { (loc, gpsId) ->
            if (loc == null) flowOf(CurrentSnapshotUi(null, null, gpsId))
            else combine(flowOf(loc), cachedRepo.observeForLocation(loc.id)) { l, w ->
                CurrentSnapshotUi(l, w, gpsId)
            }
        }.flowOn(dispatchers.io)
}
