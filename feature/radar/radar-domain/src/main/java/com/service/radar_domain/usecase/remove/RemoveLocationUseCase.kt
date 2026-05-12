package com.service.radar_domain.usecase.remove

interface RemoveLocationUseCase {
    suspend operator fun invoke(id: Int)
}
