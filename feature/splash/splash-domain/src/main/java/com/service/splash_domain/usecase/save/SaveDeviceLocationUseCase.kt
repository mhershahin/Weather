package com.service.splash_domain.usecase.save

import com.service.entity.domain.Location

interface SaveDeviceLocationUseCase {
    suspend operator fun invoke(): Location
}