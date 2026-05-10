package com.service.current_presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController


@Composable
fun CurrentDestination(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    onShowTopAlertDialogCallBack: (isErrorAlert: Boolean, errorOrAlertMessage: String?) -> Unit,
    viewModel: CurrentViewModel = hiltViewModel()
) {

    CurrentScreen(
            state = viewModel.viewState.value,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::handleEvents,
            onNavigationRequested = { navigationEffect ->
            }
        )

}