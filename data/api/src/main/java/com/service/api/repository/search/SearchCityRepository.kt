package com.service.api.repository.search

import com.service.entity.Result
import com.service.entity.response.location.GeocodingResponse

interface SearchCityRepository {
    suspend fun searchCity(name: String): Result<GeocodingResponse>
}