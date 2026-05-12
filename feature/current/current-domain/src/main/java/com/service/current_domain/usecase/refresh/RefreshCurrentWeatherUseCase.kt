package com.service.current_domain.usecase.refresh

import com.service.entity.Result
import com.service.entity.domain.Location

interface RefreshCurrentWeatherUseCase {
    suspend operator fun invoke(location: Location): Result<Unit>
}
