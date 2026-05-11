package com.service.forecast_presentation.ui

import com.service.base_ui.BaseViewModel
import com.service.entity.Result
import com.service.entity.domain.Location
import com.service.forecast_domain.usecase.ForecastSnapshot
import com.service.forecast_domain.usecase.ObserveForecastUseCase
import com.service.forecast_domain.usecase.RefreshForecastUseCase
import com.service.utils.format.formatDayDate
import com.service.utils.format.formatDayShort
import com.service.utils.format.formatRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val observe: ObserveForecastUseCase,
    private val refresh: RefreshForecastUseCase,
) : BaseViewModel<ForecastContract.Event, ForecastContract.State, ForecastContract.Effect>() {

    private var lastRefreshedLocationId: Int? = null

    init {
        launchIODispatcher {
            observe().distinctUntilChanged().collect { snap ->
                applySnapshot(snap)
                triggerRefreshIfNeeded(snap.location)
            }
        }
    }

    override fun setInitialState(): ForecastContract.State = ForecastContract.State()

    override fun handleEvents(event: ForecastContract.Event) {
        when (event) {
            ForecastContract.Event.Refresh -> lastRefreshedLocationId = null
        }
    }

    private fun triggerRefreshIfNeeded(location: Location?) {
        val id = location?.id ?: return
        if (lastRefreshedLocationId == id) return
        lastRefreshedLocationId = id
        launchIODispatcher {
            when (val res = refresh(location)) {
                is Result.Error -> setEffect {
                    ForecastContract.Effect.Dialog.ShowTopAlertDialog(
                        isErrorAlert = true,
                        errorOrAlertMessage = res.message ?: "Failed to load forecast",
                    )
                }
                is Result.Success -> Unit
            }
        }
    }

    private fun applySnapshot(snap: ForecastSnapshot) {
        val location = snap.location ?: run {
            setState { copy(isLoading = true) }
            return
        }
        val weather = snap.weather
        if (weather == null || weather.daily.isEmpty()) {
            setState {
                copy(
                    isLoading = true,
                    cityLabel = location.displayName.ifEmpty { "Current Location" },
                )
            }
            return
        }
        val rows = weather.daily.mapIndexed { idx, day ->
            ForecastContract.DayRow(
                day = formatDayShort(day.isoDate),
                date = formatDayDate(day.isoDate),
                weatherCode = day.weatherCode,
                isDay = weather.current?.isDay ?: true,
                precipPct = day.precipitationProbabilityPct,
                tempMax = day.tempMaxC,
                tempMin = day.tempMinC,
                isToday = idx == 0,
            )
        }
        val range = formatRange(
            weather.daily.firstOrNull()?.isoDate,
            weather.daily.lastOrNull()?.isoDate,
        )
        setState {
            copy(
                isLoading = false,
                errorMessage = null,
                cityLabel = location.displayName.ifEmpty { "Current Location" },
                dateRange = range,
                days = rows,
            )
        }
    }
}
