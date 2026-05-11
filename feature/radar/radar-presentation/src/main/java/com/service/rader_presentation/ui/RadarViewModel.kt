package com.service.rader_presentation.ui

import com.service.base_ui.BaseViewModel
import com.service.radar_domain.usecase.AddLocationUseCase
import com.service.radar_domain.usecase.FetchCityCardsUseCase
import com.service.radar_domain.usecase.ObserveSavedLocationsUseCase
import com.service.radar_domain.usecase.RemoveLocationUseCase
import com.service.radar_domain.usecase.SearchLocationsUseCase
import com.service.radar_domain.usecase.SelectCurrentLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
                setState { copy(saved = cards) }
            }
        }
        launchIODispatcher {
            searchQueryFlow.debounce(300).collectLatest { q ->
                val trimmed = q.trim()
                if (trimmed.length < 2) {
                    setState { copy(searchResults = emptyList(), isSearching = false) }
                    return@collectLatest
                }
                setState { copy(isSearching = true) }
                val results = searchLocations(trimmed)
                setState { copy(searchResults = results, isSearching = false) }
            }
        }
    }

    override fun setInitialState(): RadarContract.State = RadarContract.State()

    override fun handleEvents(event: RadarContract.Event) {
        when (event) {
            RadarContract.Event.OpenSearch ->
                setState { copy(isSearchOpen = true) }
            RadarContract.Event.CloseSearch ->
                setState { copy(isSearchOpen = false, searchQuery = "", searchResults = emptyList()) }
                    .also { searchQueryFlow.value = "" }
            RadarContract.Event.ToggleEdit ->
                setState { copy(isEditing = !isEditing) }
            is RadarContract.Event.SearchQueryChanged -> {
                setState { copy(searchQuery = event.query) }
                searchQueryFlow.value = event.query
            }
            is RadarContract.Event.AddCity -> {
                launchIODispatcher {
                    addLocation(event.location)
                    setState { copy(isSearchOpen = false, searchQuery = "", searchResults = emptyList()) }
                    searchQueryFlow.value = ""
                }
            }
            is RadarContract.Event.RemoveCity -> launchIODispatcher { removeLocation(event.id) }
            is RadarContract.Event.CityClicked -> {
                launchIODispatcher {
                    selectCurrent(event.location)
                    setEffect { RadarContract.Effect.Navigation.ToCurrentScreen }
                }
            }
        }
    }
}
