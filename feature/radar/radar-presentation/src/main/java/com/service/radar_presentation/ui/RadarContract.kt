package com.service.radar_presentation.ui

import com.service.base_ui.ViewEvent
import com.service.base_ui.ViewSideEffect
import com.service.base_ui.ViewState
import com.service.entity.domain.Location
import com.service.entity.ui.CityCardUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

interface RadarContract {

    sealed class Event : ViewEvent {
        data object OpenSearch : Event()
        data object CloseSearch : Event()
        data class SearchQueryChanged(val query: String) : Event()
        data class AddCity(val location: Location) : Event()
        data class RemoveCity(val id: Int) : Event()
        data class CityClicked(val location: Location) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isEditing: Boolean = false,
        val saved: ImmutableList<CityCardUi> = emptyList<CityCardUi>().toImmutableList(),
        val isSearchOpen: Boolean = false,
        val searchQuery: String = "",
        val searchResults: ImmutableList<Location> = emptyList<Location>().toImmutableList(),
        val isSearching: Boolean = false,
        val gpsLocationId: Int? = null,
        val activeLocationId: Int? = null,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val messageRes: Int) : Effect()

        sealed class Navigation : Effect() {
            data object ToDailyScreen : Navigation()
        }
    }
}
