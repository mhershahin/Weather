package com.service.splash_presentation

import com.service.base_ui.ViewEvent
import com.service.base_ui.ViewSideEffect
import com.service.base_ui.ViewState

interface SplashContract {
    sealed class Event : ViewEvent {
        data object Start : Event()
        data object PermissionGranted : Event()
        data object PermissionDenied : Event()
        data object DialogConfirmed : Event()
        data object DialogDismissed : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val showPermissionDialog: Boolean = false,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateHome : Effect()
        data object CloseApp : Effect()
        data object RequestPermission : Effect()
        data object OpenAppSettings : Effect()
    }
}