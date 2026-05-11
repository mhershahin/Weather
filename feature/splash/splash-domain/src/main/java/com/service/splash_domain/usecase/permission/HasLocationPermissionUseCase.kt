package com.service.splash_domain.usecase.permission

interface HasLocationPermissionUseCase {
    suspend operator fun invoke(): Boolean
}