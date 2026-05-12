package com.service.radar_domain.usecase.fetch

import com.service.entity.domain.Location
import com.service.entity.ui.CityCard

interface FetchCityCardsUseCase {
    suspend operator fun invoke(locations: List<Location>): List<CityCard>
}
