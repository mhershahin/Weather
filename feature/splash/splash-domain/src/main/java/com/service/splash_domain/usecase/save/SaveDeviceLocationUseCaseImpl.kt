package com.service.splash_domain.usecase.save

import com.service.db.repo.saved.SavedLocationsRepository
import com.service.entity.domain.Location
import com.service.splash_domain.usecase.location.provider.GetLocationProviderUseCase
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SaveDeviceLocationUseCaseImpl @Inject constructor(
    private val savedRepo: SavedLocationsRepository,
    private val getLocationProviderUseCase: GetLocationProviderUseCase,
    private val dispatchers: DispatcherProvider,
) : SaveDeviceLocationUseCase {

    override suspend fun invoke(): Location = withContext(dispatchers.io) {
        val location =getLocationProviderUseCase.invoke()
        savedRepo.upsertGpsLocation(location.latitude, location.longitude)
    }
}