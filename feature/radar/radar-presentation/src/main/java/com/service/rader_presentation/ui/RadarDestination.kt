package com.service.rader_presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.service.feature_api.Home

@Composable
fun RadarDestination(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    onShowTopAlertDialogCallBack: (isErrorAlert: Boolean, errorOrAlertMessage: String?) -> Unit,
    viewModel: RadarViewModel = hiltViewModel(),
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
                is RadarContract.Effect.Dialog.ShowTopAlertDialog ->
                    onShowTopAlertDialogCallBack(effect.isErrorAlert, effect.errorOrAlertMessage)
            }
        }
    }
    RadarScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = viewModel::setEvent,
        onNavigationRequested = { },
    )
}
