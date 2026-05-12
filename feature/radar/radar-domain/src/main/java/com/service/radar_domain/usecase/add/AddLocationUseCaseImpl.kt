package com.service.radar_domain.usecase.add

import com.service.db.repo.saved.SavedLocationsRepository
import com.service.entity.domain.Location
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AddLocationUseCaseImpl @Inject constructor(
    private val repo: SavedLocationsRepository,
    private val dispatchers: DispatcherProvider,
) : AddLocationUseCase {

    override suspend fun invoke(location: Location) = withContext(dispatchers.io) {
        repo.save(location)
    }
}
