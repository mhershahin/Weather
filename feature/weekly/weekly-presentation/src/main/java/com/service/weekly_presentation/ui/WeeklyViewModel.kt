package com.service.weekly_presentation.ui

import com.service.base_ui.BaseViewModel
import com.service.entity.Result
import com.service.entity.domain.Location
import com.service.entity.ui.WeeklySnapshot
import com.service.utils.format.formatDayDate
import com.service.utils.format.formatDayShort
import com.service.utils.format.formatRange
import com.service.weekly_domain.usecase.observe.ObserveWeeklyUseCase
import com.service.weekly_domain.usecase.refresh.RefreshWeeklyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class WeeklyViewModel @Inject constructor(
    private val observe: ObserveWeeklyUseCase,
    private val refresh: RefreshWeeklyUseCase,
) : BaseViewModel<WeeklyContract.Event, WeeklyContract.State, WeeklyContract.Effect>() {

    private var lastRefreshedLocationId: Int? = null

    init {
        launchMainDispatcher {
            observe().distinctUntilChanged().collect { snap ->
                applySnapshot(snap)
                triggerRefreshIfNeeded(snap.location)
            }
        }
    }

    override fun setInitialState(): WeeklyContract.State = WeeklyContract.State()

    override fun handleEvents(event: WeeklyContract.Event) {
        when (event) {
            WeeklyContract.Event.Refresh -> handelRefresh()
        }
    }

    private fun handelRefresh() {
        lastRefreshedLocationId = null
    }

    private fun triggerRefreshIfNeeded(location: Location?) {
        val id = location?.id ?: return
        if (lastRefreshedLocationId == id) return
        lastRefreshedLocationId = id
        launchMainDispatcher {
            when (val res = refresh(location)) {
                is Result.Error -> setEffect {
                    WeeklyContract.Effect.Dialog.ShowTopAlertDialog(
                        isErrorAlert = true,
                        errorOrAlertMessage = res.message ?: "Failed to load forecast",
                    )
                }
                is Result.Success -> Unit
            }
        }
    }

    private fun applySnapshot(snap: WeeklySnapshot) {
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
            WeeklyContract.DayRow(
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
