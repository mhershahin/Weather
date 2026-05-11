package com.service.splash_presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.service.base_ui.SIDE_EFFECTS_KEY
import com.service.feature_api.Home

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SplashDestination(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val perm = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION) { granted ->
        if (granted) {
            viewModel.setEvent(SplashContract.Event.PermissionGranted)
        } else {
            viewModel.setEvent(SplashContract.Event.PermissionDenied)
        }
    }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.setEvent(SplashContract.Event.Start)
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashContract.Effect.NavigateHome -> {
                    navController.navigate(Home.getRout()) {
                        popUpTo("splash") { inclusive = true }
                    }
                }
                SplashContract.Effect.CloseApp -> {
                    (context as? Activity)?.finishAndRemoveTask()
                }
                SplashContract.Effect.RequestPermission -> {
                    if (perm.status.isGranted) {
                        viewModel.setEvent(SplashContract.Event.PermissionGranted)
                    } else {
                        perm.launchPermissionRequest()
                    }
                }
                SplashContract.Effect.OpenAppSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    SplashScreen(
        state = viewModel.viewState.value,
        onDialogConfirm = { viewModel.setEvent(SplashContract.Event.DialogConfirmed) },
        onDialogDismiss = { viewModel.setEvent(SplashContract.Event.DialogDismissed) },
    )
}