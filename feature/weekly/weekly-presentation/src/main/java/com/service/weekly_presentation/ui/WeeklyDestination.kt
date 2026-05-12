package com.service.weekly_presentation.ui

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun WeeklyDestination(
    viewModel: WeeklyViewModel = hiltViewModel()
) {
    WeeklyScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = viewModel::setEvent,
    )
}
