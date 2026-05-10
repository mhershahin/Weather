package com.service.api.repository.search

import com.service.entity.response.location.GeocodingResponse
import com.service.entity.Result
interface SearchCityRepository {
    suspend fun searchCity(name: String): Result<GeocodingResponse>
}