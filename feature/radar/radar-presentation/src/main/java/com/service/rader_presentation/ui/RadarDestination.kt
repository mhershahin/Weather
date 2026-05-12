package com.service.rader_presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.service.feature_api.Home

@Composable
fun RadarDestination(
    navController: NavHostController,
    viewModel: RadarViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RadarContract.Effect.Navigation.ToDailyScreen -> {
                    navController.navigate(Home.Daily.getRout()) {
                        popUpTo(Home.Radar.getRout()) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        }
    }
    RadarScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = viewModel::setEvent,
    )
}
