package com.service.radar_presentation.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.service.feature_api.Home

@Composable
fun RadarDestination(
    navController: NavHostController,
    viewModel: RadarViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RadarContract.Effect.Navigation.ToDailyScreen -> {
                    navController.navigate(Home.Daily.getRout()) {
                        popUpTo(Home.Radar.getRout()) { inclusive = false }
                        launchSingleTop = true
                    }
                }
                is RadarContract.Effect.ShowToast -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    RadarScreen(
        state = viewModel.viewState.value,
        onEventSent = viewModel::setEvent,
    )
}
