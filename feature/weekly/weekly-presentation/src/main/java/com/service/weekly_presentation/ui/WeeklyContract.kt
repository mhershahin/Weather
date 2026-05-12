package com.service.weekly_presentation.ui

import com.service.base_ui.ViewEvent
import com.service.base_ui.ViewSideEffect
import com.service.base_ui.ViewState

interface WeeklyContract {

    sealed class Event : ViewEvent {
        data object Refresh : Event()
    }

    data class DayRow(
        val day: String,
        val date: String,
        val weatherCode: Int,
        val isDay: Boolean,
        val precipPct: Int,
        val tempMax: Int,
        val tempMin: Int,
        val isToday: Boolean,
    )

    data class State(
        val isLoading: Boolean = true,
        val errorMessage: String? = null,
        val cityLabel: String = "Current Location",
        val dateRange: String = "",
        val days: List<DayRow> = emptyList(),
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect()
        sealed class Dialog : Effect() {
            data class ShowTopAlertDialog(
                val isErrorAlert: Boolean,
                val errorOrAlertMessage: String?,
            ) : Dialog()
        }
    }
}
