package com.service.rader_presentation.ui

import com.service.base_ui.BaseViewModel
import com.service.entity.domain.Location
import com.service.radar_domain.usecase.AddLocationUseCase
import com.service.radar_domain.usecase.FetchCityCardsUseCase
import com.service.radar_domain.usecase.ObserveSavedLocationsUseCase
import com.service.radar_domain.usecase.RemoveLocationUseCase
import com.service.radar_domain.usecase.SearchLocationsUseCase
import com.service.radar_domain.usecase.SelectCurrentLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class RadarViewModel @Inject constructor(
    private val observeSaved: ObserveSavedLocationsUseCase,
    private val searchLocations: SearchLocationsUseCase,
    private val addLocation: AddLocationUseCase,
    private val removeLocation: RemoveLocationUseCase,
    private val selectCurrent: SelectCurrentLocationUseCase,
    private val fetchCards: FetchCityCardsUseCase,
) : BaseViewModel<RadarContract.Event, RadarContract.State, RadarContract.Effect>() {

    private val searchQueryFlow = MutableStateFlow("")

    init {
        launchIODispatcher {
            observeSaved().distinctUntilChanged().collect { list ->
                val cards = fetchCards(list)
                setState { copy(saved = cards.toImmutableList()) }
            }
        }
        launchIODispatcher {
            searchQueryFlow.debounce(300).collectLatest { q ->
                val trimmed = q.trim()
                if (trimmed.length < 2) {
                    setState { copy(searchResults = emptyList<Location>().toImmutableList(), isSearching = false) }
                    return@collectLatest
                }
                setState { copy(isSearching = true) }
                val results = searchLocations(trimmed)
                setState { copy(searchResults = results.toImmutableList(), isSearching = false) }
            }
        }
    }

    override fun setInitialState(): RadarContract.State = RadarContract.State()

    override fun handleEvents(event: RadarContract.Event) {
        when (event) {
            is RadarContract.Event.OpenSearch -> handelOpenSearch()
            is RadarContract.Event.CloseSearch -> handelCloseSearch()
            is RadarContract.Event.SearchQueryChanged -> handelSearchQueryChanged(event.query)
            is RadarContract.Event.AddCity -> handelAddCity(event.location)
            is RadarContract.Event.RemoveCity -> handelRemoveCity(event.id)
            is RadarContract.Event.CityClicked -> handelCityClicked(event.location)
        }
    }

    private fun handelOpenSearch() {
        setState { copy(isSearchOpen = true) }
    }

    private fun handelCloseSearch() {
        setState { copy(isSearchOpen = false, searchQuery = "", searchResults = emptyList<Location>().toImmutableList()) }
        searchQueryFlow.value = ""
    }

    private fun handelSearchQueryChanged(query: String) {
        setState { copy(searchQuery = query) }
        searchQueryFlow.value = query
    }

    private fun handelAddCity(location: Location) {
        launchIODispatcher {
            addLocation(location)
            setState { copy(isSearchOpen = false, searchQuery = "", searchResults = emptyList<Location>().toImmutableList()) }
            searchQueryFlow.value = ""
        }
    }

    private fun handelRemoveCity(id: Int) {
        launchIODispatcher { removeLocation(id) }
    }

    private fun handelCityClicked(location: Location) {
        launchIODispatcher {
            selectCurrent(location)
            setEffect { RadarContract.Effect.Navigation.ToDailyScreen }
        }
    }
}