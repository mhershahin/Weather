package com.service.daily_presentation.ui

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun DailyDestination(
    viewModel: DailyViewModel = hiltViewModel()
) {
    DailyScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = viewModel::setEvent,
    )
}
