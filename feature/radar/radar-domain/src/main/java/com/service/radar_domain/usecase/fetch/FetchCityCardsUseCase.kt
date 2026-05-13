package com.service.radar_domain.usecase.fetch

import com.service.entity.domain.Location
import com.service.entity.ui.CityCardUi

interface FetchCityCardsUseCase {
    suspend operator fun invoke(locations: List<Location>): List<CityCardUi>
}
