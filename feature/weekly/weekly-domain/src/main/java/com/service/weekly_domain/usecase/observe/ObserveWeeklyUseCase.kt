package com.service.weekly_domain.usecase.observe

import kotlinx.coroutines.flow.Flow

interface ObserveWeeklyUseCase {
    operator fun invoke(): Flow<WeeklySnapshot>
}
