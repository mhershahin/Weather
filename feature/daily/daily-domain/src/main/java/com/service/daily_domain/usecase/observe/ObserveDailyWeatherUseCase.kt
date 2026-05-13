package com.service.daily_domain.usecase.observe

import com.service.entity.ui.CurrentSnapshotUi
import kotlinx.coroutines.flow.Flow

interface ObserveDailyWeatherUseCase {
    operator fun invoke(): Flow<CurrentSnapshotUi>
}
