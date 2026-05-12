package com.service.radar_domain.usecase.select

import com.service.entity.domain.Location

interface UpdateWeatherDataUseCase {
    suspend operator fun invoke(location: Location)
}
