package com.service.radar_domain.usecase.observe

import com.service.db.repo.saved.SavedLocationsRepository
import com.service.entity.domain.Location
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class ObserveSavedLocationsUseCaseImpl @Inject constructor(
    private val repo: SavedLocationsRepository,
    private val dispatchers: DispatcherProvider,
) : ObserveSavedLocationsUseCase {

    override fun invoke(): Flow<List<Location>> =
        repo.observeSaved().flowOn(dispatchers.io)
}
