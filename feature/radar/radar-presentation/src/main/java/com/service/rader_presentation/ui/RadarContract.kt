package com.service.rader_presentation.ui

import com.service.base_ui.ViewEvent
import com.service.base_ui.ViewSideEffect
import com.service.base_ui.ViewState
import com.service.entity.domain.Location
import com.service.radar_domain.usecase.CityCard

interface RadarContract {

    sealed class Event : ViewEvent {
        data object OpenSearch : Event()
        data object CloseSearch : Event()
        data object ToggleEdit : Event()
        data class SearchQueryChanged(val query: String) : Event()
        data class AddCity(val location: Location) : Event()
        data class RemoveCity(val id: Int) : Event()
        data class CityClicked(val location: Location) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isEditing: Boolean = false,
        val saved: List<CityCard> = emptyList(),
        val isSearchOpen: Boolean = false,
        val searchQuery: String = "",
        val searchResults: List<Location> = emptyList(),
        val isSearching: Boolean = false,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object ToDailyScreen : Navigation()
        }
        sealed class Dialog : Effect() {
            data class ShowTopAlertDialog(
                val isErrorAlert: Boolean,
                val errorOrAlertMessage: String?,
            ) : Dialog()
        }
    }
}
