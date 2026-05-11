package com.service.splash_presentation

import com.service.base_ui.BaseViewModel
import com.service.splash_domain.usecase.location.GetCurrentLocationUseCase
import com.service.splash_domain.usecase.permission.HasLocationPermissionUseCase
import com.service.splash_domain.usecase.save.SaveDeviceLocationUseCase
import com.service.splash_domain.usecase.update.UpdateAllDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val saveDeviceLocationUseCase: SaveDeviceLocationUseCase,
    private val hasLocationPermissionUseCase: HasLocationPermissionUseCase,
    private val updateAllDataUseCase: UpdateAllDataUseCase,
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override fun setInitialState(): SplashContract.State = SplashContract.State()

    override fun handleEvents(event: SplashContract.Event) {
        when (event) {
            SplashContract.Event.Start -> handelStart()
            SplashContract.Event.PermissionGranted -> handelPermissionGranted()
            SplashContract.Event.PermissionDenied -> handelPermissionDenied()
            SplashContract.Event.DialogConfirmed -> handelDialogConfirmed()
            SplashContract.Event.DialogDismissed -> handelDialogDismissed()
        }
    }

    private fun handelPermissionGranted() {
        launchMainDispatcher {
            saveDeviceLocationUseCase.invoke()
            updateAllDataUseCase.invoke()
            setEffect { SplashContract.Effect.NavigateHome }
        }
    }

    private fun handelDialogConfirmed() {
        launchMainDispatcher {
            setState { copy(showPermissionDialog = false) }
            setEffect { SplashContract.Effect.OpenAppSettings }
        }
    }

    private fun handelDialogDismissed() {
        launchMainDispatcher {
            setState { copy(showPermissionDialog = false) }
            setEffect { SplashContract.Effect.CloseApp }
        }
    }

    private fun handelStart() {
        launchMainDispatcher {
            setState { copy(isLoading = true) }
            val current = getCurrentLocationUseCase.invoke()
            if (current != null) {
                updateAllDataUseCase.invoke(current)
                setEffect { SplashContract.Effect.NavigateHome }
            } else if (hasLocationPermissionUseCase.invoke()) {
                handelPermissionGranted()
            } else {
                setEffect { SplashContract.Effect.RequestPermission }
            }
        }
    }

    private fun handelPermissionDenied() {
        launchMainDispatcher {
            setState { copy(showPermissionDialog = true) }
        }
    }
}

