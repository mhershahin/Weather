package com.service.daily_domain.usecase.refresh

import com.service.entity.Result
import com.service.entity.domain.Location

interface RefreshDailyWeatherUseCase {
    suspend operator fun invoke(location: Location): Result<Unit>
}
