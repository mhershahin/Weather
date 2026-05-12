package com.service.daily_presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun DailyDestination(
    navController: NavHostController,
    onShowTopAlertDialogCallBack: (isErrorAlert: Boolean, errorOrAlertMessage: String?) -> Unit,
    viewModel: DailyViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is DailyContract.Effect.Dialog.ShowTopAlertDialog ->
                    onShowTopAlertDialogCallBack(effect.isErrorAlert, effect.errorOrAlertMessage)
                else -> Unit
            }
        }
    }
    DailyScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = viewModel::setEvent,
        onNavigationRequested = { },
    )
}
