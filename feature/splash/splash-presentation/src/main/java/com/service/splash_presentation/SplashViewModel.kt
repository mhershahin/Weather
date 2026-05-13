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
            SplashContract.Event.Start -> handleStart()
            SplashContract.Event.PermissionGranted -> handlePermissionGranted()
            SplashContract.Event.PermissionDenied -> handlePermissionDenied()
            SplashContract.Event.DialogConfirmed -> handleDialogConfirmed()
            SplashContract.Event.DialogDismissed -> handleDialogDismissed()
        }
    }

    private fun handlePermissionGranted() {
        reload()
    }

    private fun reload(){
        launchMainDispatcher {
            val savedLocation = saveDeviceLocationUseCase.invoke()
            updateAllDataUseCase.invoke(savedLocation)
            setEffect { SplashContract.Effect.NavigateHome }
        }
    }

    private fun handleDialogConfirmed() {
        launchMainDispatcher {
            setState { copy(showPermissionDialog = false) }
            setEffect { SplashContract.Effect.OpenAppSettings }
        }
    }

    private fun handleDialogDismissed() {
        launchMainDispatcher {
            setState { copy(showPermissionDialog = false) }
            setEffect { SplashContract.Effect.CloseApp }
        }
    }

    private fun handleStart() {
        launchMainDispatcher {
            setState { copy(isLoading = true) }
            val current = getCurrentLocationUseCase.invoke()
            if (current != null) {
                updateAllDataUseCase.invoke(current)
                setEffect { SplashContract.Effect.NavigateHome }
            } else if (hasLocationPermissionUseCase.invoke()) {
                handlePermissionGranted()
            } else {
                setEffect { SplashContract.Effect.RequestPermission }
            }
        }
    }

    private fun handlePermissionDenied() {
        launchMainDispatcher {
            setState { copy(showPermissionDialog = true) }
        }
    }
}

