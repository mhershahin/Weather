package com.service.splash_domain.usecase.update

import com.service.entity.domain.Location

interface UpdateAllDataUseCase {
    suspend operator fun invoke(location: Location? = null)
}