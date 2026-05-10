package com.service.current_presentation.ui

import com.service.base_ui.ViewEvent
import com.service.base_ui.ViewSideEffect
import com.service.base_ui.ViewState

interface CurrentContract {
    sealed class Event : ViewEvent {

    }

    data class State(
        val isLoading: Boolean = false,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {}
        sealed class Dialog : Effect() {
            data class ShowTopAlertDialog(
                val isErrorAlert: Boolean,
                val errorOrAlertMessage: String?
            ) : Dialog()
        }
    }
}
