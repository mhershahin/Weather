package com.service.daily_presentation.ui

import com.service.base_ui.BaseViewModel
import com.service.daily_domain.usecase.observe.ObserveDailyWeatherUseCase
import com.service.daily_domain.usecase.refresh.RefreshDailyWeatherUseCase
import com.service.entity.Result
import com.service.entity.domain.Location
import com.service.entity.domain.Weather
import com.service.entity.ui.CurrentSnapshot
import com.service.utils.format.formatHourLabel
import com.service.utils.format.parseHour
import com.service.utils.weather.WeatherCodeMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val observe: ObserveDailyWeatherUseCase,
    private val refresh: RefreshDailyWeatherUseCase,
) : BaseViewModel<DailyContract.Event, DailyContract.State, DailyContract.Effect>() {

    private var lastRefreshedLocationId: Int? = null

    init {
        launchMainDispatcher {
            observe().collect { snap ->
                applySnapshot(snap)
                triggerRefreshIfNeeded(snap.location)
            }
        }
    }

    override fun setInitialState(): DailyContract.State = DailyContract.State()

    override fun handleEvents(event: DailyContract.Event) {
        when (event) {
            DailyContract.Event.Refresh -> handelRefresh()
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
                    DailyContract.Effect.Dialog.ShowTopAlertDialog(
                        isErrorAlert = true,
                        errorOrAlertMessage = res.message ?: "Failed to load weather",
                    )
                }
                is Result.Success -> Unit
            }
        }
    }

    private fun applySnapshot(snap: CurrentSnapshot) {
        val location = snap.location
        val weather = snap.weather
        if (location == null) {
            setState { copy(isLoading = true) }
            return
        }
        if (weather == null) {
            setState {
                copy(
                    isLoading = true,
                    cityLabel = location.displayName.ifEmpty { "Current Location" },
                )
            }
            return
        }
        setState { reduce(location, weather) }
    }

    private fun DailyContract.State.reduce(location: Location, weather: Weather): DailyContract.State {
        val c = weather.current
        val isDay = c?.isDay ?: true
        val nowIdx = currentHourIndex(weather.hourly.map { it.isoTime })
        val slots = (0 until 24).mapNotNull { off ->
            weather.hourly.getOrNull(nowIdx + off)?.let { h ->
                DailyContract.HourSlot(
                    label = formatHourLabel(h.isoTime, isNow = off == 0),
                    tempC = h.tempC,
                    weatherCode = h.weatherCode,
                    isDay = h.isDay,
                )
            }
        }
        val visMi = c?.visibilityMeters?.let { (it / 1609.34).roundToInt() }
        val visNote = when {
            visMi == null -> ""
            visMi >= 6 -> "Perfectly clear"
            visMi >= 3 -> "Mostly clear"
            else -> "Reduced visibility"
        }
        return copy(
            isLoading = false,
            errorMessage = null,
            cityLabel = location.displayName.ifEmpty { "Current Location" },
            tempC = c?.tempC,
            condition = WeatherCodeMapper.label(c?.weatherCode),
            highC = weather.highC,
            lowC = weather.lowC,
            weatherCode = c?.weatherCode,
            isDay = isDay,
            hourly = slots,
            uvIndex = c?.uvIndex,
            uvLabel = uvLabel(c?.uvIndex),
            humidityPct = c?.humidityPct,
            dewPointC = c?.dewPointC,
            windKmh = c?.windKmh,
            windDirText = c?.windDirectionDeg?.let { degToCardinal(it) }.orEmpty(),
            visibilityMi = visMi,
            visibilityNote = visNote,
        )
    }

    private fun currentHourIndex(times: List<String>): Int {
        if (times.isEmpty()) return 0
        val now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0)
        val idx = times.indexOfFirst { iso ->
            val t = parseHour(iso) ?: return@indexOfFirst false
            !t.isBefore(now)
        }
        return if (idx == -1) 0 else idx
    }

    private fun uvLabel(uv: Int?): String = when {
        uv == null -> ""
        uv <= 2 -> "Low"
        uv <= 5 -> "Moderate"
        uv <= 7 -> "High"
        uv <= 10 -> "Very High"
        else -> "Extreme"
    }

    private fun degToCardinal(deg: Int): String {
        val dirs = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
        val idx = ((deg % 360) / 45.0).toInt() % 8
        return dirs[idx]
    }
}
