package com.service.splash_domain.usecase.location

import com.service.entity.domain.Location

interface GetCurrentLocationUseCase {
    suspend operator fun invoke(): Location?
}