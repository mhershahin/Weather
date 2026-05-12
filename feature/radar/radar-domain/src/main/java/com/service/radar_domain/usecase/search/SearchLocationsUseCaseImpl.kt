package com.service.radar_domain.usecase.search

import com.service.api.repository.search.SearchCityRepository
import com.service.entity.Result
import com.service.entity.domain.Location
import com.service.entity.domain.LocationKind
import com.service.entity.domain.toLocation
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SearchLocationsUseCaseImpl @Inject constructor(
    private val searchRepo: SearchCityRepository,
    private val dispatchers: DispatcherProvider,
) : SearchLocationsUseCase {

    override suspend fun invoke(query: String): List<Location> = withContext(dispatchers.io) {
        if (query.length < 2) return@withContext emptyList()
        when (val res = searchRepo.searchCity(query)) {
            is Result.Success -> res.data?.results.orEmpty().map { it.toLocation(LocationKind.SAVED) }
            is Result.Error -> emptyList()
        }
    }
}
