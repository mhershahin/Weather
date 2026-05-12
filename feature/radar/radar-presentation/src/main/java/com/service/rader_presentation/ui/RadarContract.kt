package com.service.rader_presentation.ui

import com.service.base_ui.ViewEvent
import com.service.base_ui.ViewSideEffect
import com.service.base_ui.ViewState
import com.service.entity.domain.Location
import com.service.entity.ui.CityCard
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
        val saved: ImmutableList<CityCard> = emptyList<CityCard>().toImmutableList(),
        val isSearchOpen: Boolean = false,
        val searchQuery: String = "",
        val searchResults: ImmutableList<Location> = emptyList<Location>().toImmutableList(),
        val isSearching: Boolean = false,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object ToDailyScreen : Navigation()
        }

        sealed class Dialog : Effect() {
        }
    }
}
