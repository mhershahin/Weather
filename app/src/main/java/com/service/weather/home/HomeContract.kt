package com.service.weather.home

import com.service.base_ui.ViewEvent
import com.service.base_ui.ViewSideEffect
import com.service.base_ui.ViewState


interface HomeContract {

    sealed class Event() : ViewEvent {
    }

    data class State(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
        }
    }
}