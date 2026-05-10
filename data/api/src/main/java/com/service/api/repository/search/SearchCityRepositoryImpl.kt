package com.service.api.repository.search

import com.service.api.service.geocoding.GeocodingApiService
import com.service.entity.response.location.GeocodingResponse
import javax.inject.Inject
import com.service.entity.Result
import com.service.utils.analyzeResponse
import com.service.utils.makeApiCall

internal class SearchCityRepositoryImpl @Inject constructor(
    private val geocodingApiService: GeocodingApiService
) : SearchCityRepository {

    override suspend fun searchCity(name: String): Result<GeocodingResponse> {
        return when (val result = makeApiCall({
            analyzeResponse(
                geocodingApiService.searchCities(name)
            )
        })) {
            is Result.Success -> result
            is Result.Error -> result
        }    }
}