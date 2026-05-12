package com.service.weekly_domain.usecase.refresh

import com.service.entity.Result
import com.service.entity.domain.Location

interface RefreshWeeklyUseCase {
    suspend operator fun invoke(location: Location): Result<Unit>
}
