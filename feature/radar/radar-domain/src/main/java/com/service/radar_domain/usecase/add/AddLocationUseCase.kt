package com.service.radar_domain.usecase.add

import com.service.entity.domain.Location

interface AddLocationUseCase {
    suspend operator fun invoke(location: Location)
}
