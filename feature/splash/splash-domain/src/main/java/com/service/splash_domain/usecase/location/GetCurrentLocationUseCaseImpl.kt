package com.service.splash_domain.usecase.location

import com.service.db.repo.saved.SavedLocationsRepository
import com.service.entity.domain.Location
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetCurrentLocationUseCaseImpl @Inject constructor(
    private val savedRepo: SavedLocationsRepository,
    private val dispatchers: DispatcherProvider,
) : GetCurrentLocationUseCase {

    override suspend fun invoke(): Location? = withContext(dispatchers.io) {
        savedRepo.observeCurrent().firstOrNull()
    }
}