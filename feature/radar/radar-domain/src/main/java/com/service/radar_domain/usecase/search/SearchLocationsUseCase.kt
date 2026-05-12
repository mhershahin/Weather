package com.service.radar_domain.usecase.search

import com.service.entity.domain.Location

interface SearchLocationsUseCase {
    suspend operator fun invoke(query: String): List<Location>
}
