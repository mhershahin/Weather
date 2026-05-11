package com.service.splash_domain.usecase.location.provider

import com.service.entity.LatLon

interface GetLocationProviderUseCase {

    suspend operator fun invoke(): LatLon
}