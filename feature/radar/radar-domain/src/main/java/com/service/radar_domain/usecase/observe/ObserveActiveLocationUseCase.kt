package com.service.radar_domain.usecase.observe

import com.service.entity.domain.Location
import kotlinx.coroutines.flow.Flow

interface ObserveActiveLocationUseCase {
    operator fun invoke(): Flow<Location?>
}
