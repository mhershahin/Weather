package com.service.rader_presentation.ui

import com.service.base_ui.BaseViewModel
import com.service.base_ui.R
import com.service.entity.domain.Location
import com.service.radar_domain.usecase.add.AddLocationUseCase
import com.service.radar_domain.usecase.fetch.FetchCityCardsUseCase
import com.service.radar_domain.usecase.observe.ObserveActiveLocationUseCase
import com.service.radar_domain.usecase.observe.ObserveGpsLocationUseCase
import com.service.radar_domain.usecase.observe.ObserveSavedLocationsUseCase
import com.service.radar_domain.usecase.remove.RemoveLocationUseCase
import com.service.radar_domain.usecase.search.SearchLocationsUseCase
import com.service.radar_domain.usecase.select.UpdateWeatherDataUseCase
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
    private val observeGps: ObserveGpsLocationUseCase,
    private val observeActive: ObserveActiveLocationUseCase,
    private val searchLocations: SearchLocationsUseCase,
    private val addLocation: AddLocationUseCase,
    private val removeLocation: RemoveLocationUseCase,
    private val updateWeatherDataUseCase: UpdateWeatherDataUseCase,
    private val fetchCards: FetchCityCardsUseCase,
) : BaseViewModel<RadarContract.Event, RadarContract.State, RadarContract.Effect>() {

    private val searchQueryFlow = MutableStateFlow("")

    init {
        launchMainDispatcher {
            observeSaved().distinctUntilChanged().collect { list ->
                val cards = fetchCards(list)
                setState { copy(saved = cards.toImmutableList()) }
            }
        }
        launchMainDispatcher {
            observeGps().distinctUntilChanged().collect { gps ->
                setState { copy(gpsLocationId = gps?.id) }
            }
        }
        launchMainDispatcher {
            observeActive().distinctUntilChanged().collect { active ->
                setState { copy(activeLocationId = active?.id) }
            }
        }
        launchMainDispatcher {
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
        launchMainDispatcher{
            setState { copy(isSearchOpen = true) }
        }
    }

    private fun handelCloseSearch() {
        launchMainDispatcher{
            setState { copy(isSearchOpen = false, searchQuery = "", searchResults = emptyList<Location>().toImmutableList()) }
            searchQueryFlow.value = ""
        }
    }

    private fun handelSearchQueryChanged(query: String) {
        launchMainDispatcher{
            setState { copy(searchQuery = query) }
            searchQueryFlow.value = query
        }
    }

    private fun handelAddCity(location: Location) {
        launchMainDispatcher {
            addLocation.invoke(location)
            setState { copy(isSearchOpen = false, searchQuery = "", searchResults = emptyList<Location>().toImmutableList()) }
            searchQueryFlow.value = ""
            handelCityClicked(location)
        }
    }

    private fun handelRemoveCity(id: Int) {
        val state = viewState.value
        if (state.gpsLocationId == id) {
            setEffect { RadarContract.Effect.ShowToast(R.string.cant_delete_current_location) }
            return
        }
        if (state.saved.size <= 1) {
            setEffect { RadarContract.Effect.ShowToast(R.string.cant_delete_last_location) }
            return
        }
        val wasActive = state.activeLocationId == id
        val gpsCity = state.saved.firstOrNull { it.location.id == state.gpsLocationId }?.location
        launchMainDispatcher {
            if (wasActive && gpsCity != null) {
                setState { copy(isLoading = true) }
                removeLocation.invoke(id)
                updateWeatherDataUseCase.invoke(gpsCity)
                setState { copy(isLoading = false) }
            } else {
                removeLocation.invoke(id)
            }
        }
    }

    private fun handelCityClicked(location: Location) {
        launchMainDispatcher {
            setState { copy(isLoading = true) }
            updateWeatherDataUseCase.invoke(location)
            setState { copy(isLoading = false) }
            setEffect { RadarContract.Effect.Navigation.ToDailyScreen }
        }
    }
}
