package com.service.api.service.geocoding

import com.service.api.service.geocoding.GeocodingConstants.Companion.GEOCODING_SEARCH
import com.service.api.service.geocoding.GeocodingConstants.Companion.SEARCH_FORMAT
import com.service.api.service.geocoding.GeocodingConstants.Companion.SEARCH_LANGUAGE
import com.service.api.service.geocoding.GeocodingConstants.Companion.SEARCH_RESULT_COUNT
import com.service.entity.response.location.GeocodingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {

    @GET(GEOCODING_SEARCH)
    suspend fun searchCities(
        @Query("name") name: String,
        @Query("count") count: Int = SEARCH_RESULT_COUNT,
        @Query("language") language: String = SEARCH_LANGUAGE,
        @Query("format") format: String = SEARCH_FORMAT
    ): Response<GeocodingResponse>
}