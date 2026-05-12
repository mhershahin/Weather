package com.service.splash_domain.usecase.save

import com.service.api.repository.search.SearchCityRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.entity.Result
import com.service.entity.domain.Location
import com.service.entity.domain.toLocation
import com.service.splash_domain.usecase.location.provider.GetLocationProviderUseCase
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SaveDeviceLocationUseCaseImpl @Inject constructor(
    private val savedRepo: SavedLocationsRepository,
    private val getLocationProviderUseCase: GetLocationProviderUseCase,
    private val searchCityRepository: SearchCityRepository,
    private val dispatchers: DispatcherProvider
) : SaveDeviceLocationUseCase {

    override suspend fun invoke(): Location = withContext(dispatchers.io) {
        val location = getLocationProviderUseCase.invoke()
        val reverseLocation =
            searchCityRepository.searchCity(location.name ?: Location.getDefault().name)
        val apiLocation =
            if (reverseLocation is Result.Success) reverseLocation.data?.results?.firstOrNull()
                ?.toLocation() ?: Location.getDefault() else Location.getDefault()
        savedRepo.save(apiLocation)
        return@withContext apiLocation

    }


}