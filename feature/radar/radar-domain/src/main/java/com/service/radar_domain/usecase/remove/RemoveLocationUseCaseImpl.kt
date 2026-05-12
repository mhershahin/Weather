package com.service.radar_domain.usecase.remove

import com.service.db.repo.saved.SavedLocationsRepository
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RemoveLocationUseCaseImpl @Inject constructor(
    private val repo: SavedLocationsRepository,
    private val dispatchers: DispatcherProvider,
) : RemoveLocationUseCase {

    override suspend fun invoke(id: Int) = withContext(dispatchers.io) {
        repo.remove(id)
    }
}
