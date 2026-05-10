package com.service.rader_presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun RadarDestination(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    onShowTopAlertDialogCallBack: (isErrorAlert: Boolean, errorOrAlertMessage: String?) -> Unit,
    viewModel: RadarViewModel = hiltViewModel()
) {

    RadarScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = viewModel::handleEvents,
        onNavigationRequested = { navigationEffect ->
        }
    )

}