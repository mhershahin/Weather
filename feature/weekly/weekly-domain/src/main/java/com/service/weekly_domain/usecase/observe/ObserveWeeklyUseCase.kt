package com.service.weekly_domain.usecase.observe

import com.service.entity.ui.WeeklySnapshotUi
import kotlinx.coroutines.flow.Flow

interface ObserveWeeklyUseCase {
    operator fun invoke(): Flow<WeeklySnapshotUi>
}
