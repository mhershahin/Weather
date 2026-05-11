package com.service.current_presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun CurrentDestination(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    onShowTopAlertDialogCallBack: (isErrorAlert: Boolean, errorOrAlertMessage: String?) -> Unit,
    viewModel: CurrentViewModel = hiltViewModel(),
) {
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CurrentContract.Effect.Dialog.ShowTopAlertDialog ->
                    onShowTopAlertDialogCallBack(effect.isErrorAlert, effect.errorOrAlertMessage)
                else -> Unit
            }
        }
    }
    CurrentScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = viewModel::setEvent,
        onNavigationRequested = { },
    )
}
