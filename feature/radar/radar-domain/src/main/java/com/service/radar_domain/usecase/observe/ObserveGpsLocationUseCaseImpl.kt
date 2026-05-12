package com.service.radar_domain.usecase.observe

import com.service.db.repo.saved.SavedLocationsRepository
import com.service.entity.domain.Location
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class ObserveGpsLocationUseCaseImpl @Inject constructor(
    private val repo: SavedLocationsRepository,
    private val dispatchers: DispatcherProvider,
) : ObserveGpsLocationUseCase {

    override fun invoke(): Flow<Location?> =
        repo.observeGps().flowOn(dispatchers.io)
}
