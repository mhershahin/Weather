package com.service.daily_presentation.ui

import com.service.base_ui.ViewEvent
import com.service.base_ui.ViewSideEffect
import com.service.base_ui.ViewState
import com.service.entity.ui.HourSlot

interface DailyContract {

    sealed class Event : ViewEvent {
        data object Refresh : Event()
    }



    data class State(
        val isLoading: Boolean = true,
        val errorMessage: String? = null,
        val cityLabel: String = "",
        val tempC: Int? = null,
        val condition: String = "",
        val highC: Int? = null,
        val lowC: Int? = null,
        val weatherCode: Int? = null,
        val isDay: Boolean = true,
        val hourly: List<HourSlot> = emptyList(),
        val uvIndex: Int? = null,
        val uvLabel: String = "",
        val humidityPct: Int? = null,
        val dewPointC: Int? = null,
        val windKmh: Int? = null,
        val windDirText: String = "",
        val visibilityMi: Int? = null,
        val visibilityNote: String = "",
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect()
        sealed class Dialog : Effect() {
        }
    }
}
