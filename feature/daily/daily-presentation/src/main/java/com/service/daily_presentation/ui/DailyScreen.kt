package com.service.daily_presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.service.base_ui.R
import com.service.base_ui.ScaffoldSnackFree
import com.service.base_ui.components.AppTopBar
import com.service.base_ui.components.ErrorState
import com.service.base_ui.components.HourlyForecastItem
import com.service.base_ui.components.LoadingState
import com.service.base_ui.components.SectionHeader
import com.service.base_ui.components.StatCard
import com.service.utils.ui.LocalSpacing
import com.service.utils.ui.LocalTextSize
import com.service.utils.weather.WeatherCodeMapper
import kotlinx.coroutines.flow.Flow

@Composable
internal fun DailyScreen(
    state: DailyContract.State,
    effectFlow: Flow<DailyContract.Effect>?,
    onEventSent: (event: DailyContract.Event) -> Unit,
) {
    ScaffoldSnackFree(backgroundColor = MaterialTheme.colors.background) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            AppTopBar(title = stringResource(R.string.daily))
            when {
                state.isLoading && state.tempC == null -> LoadingState()
                state.errorMessage != null && state.tempC == null ->
                    ErrorState(message = state.errorMessage)
                else -> DailyContent(state)
            }
        }
    }
    BackHandler {

    }
}

@Composable
private fun DailyContent(state: DailyContract.State) {
    val spacing = LocalSpacing.current
    val sizes = LocalTextSize.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = spacing.sixteenDp),
        verticalArrangement = Arrangement.spacedBy(spacing.eightDp)
    ) {

        Spacer(Modifier.width(spacing.eightDp))
        Text(
            text = stringResource(R.string.current_location).uppercase(),
            color = MaterialTheme.colors.primary,
            fontSize = sizes.elevenSp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = state.cityLabel,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.SemiBold,
            fontSize = sizes.twentyEightSp,
            textAlign = TextAlign.Center,
        )
        Text(
            text = state.tempC?.let { "${it}°" } ?: "—",
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Light,
            fontSize = sizes.sixtySP,
            textAlign = TextAlign.Center,
        )
        Text(
            text = state.condition,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.onBackground,
            fontSize = sizes.sixteenSp,
            textAlign = TextAlign.Center,
        )
        if (state.highC != null && state.lowC != null) {
            Text(
                text = "H: ${state.highC}°    L: ${state.lowC}°",
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary,
                fontSize = sizes.thirteenSp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
            )
        }
        Surface(
            color = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(spacing.sixteenDp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(spacing.sixteenDp)) {
                SectionHeader(title = stringResource(R.string.hourly_forecast))
                Spacer(Modifier.height(spacing.twelveDp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing.twelveDp),
                ) {
                    items(state.hourly, key = { it.label }) { slot ->
                        HourlyForecastItem(
                            label = slot.label,
                            icon = WeatherCodeMapper.icon(slot.weatherCode, slot.isDay),
                            temp = "${slot.tempC}°"
                        )
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            StatCard(
                icon = Icons.Filled.WbSunny,
                label = stringResource(R.string.uv_index),
                value = state.uvIndex?.toString() ?: "—",
                subText = state.uvLabel.ifEmpty { null },
                progress = state.uvIndex?.let { (it / 11f).coerceIn(0f, 1f) },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(spacing.eightDp))
            StatCard(
                icon = WeatherCodeMapper.Humidity,
                label = stringResource(R.string.humidity),
                value = state.humidityPct?.let { "$it%" } ?: "—",
                subText = state.dewPointC?.let { "Dew point ${it}°" },
                modifier = Modifier.weight(1f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            StatCard(
                icon = WeatherCodeMapper.Wind,
                label = stringResource(R.string.wind),
                value = state.windKmh?.let { "$it km/h" } ?: "—",
                subText = state.windDirText.takeIf { it.isNotEmpty() }?.let { "From $it" },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(spacing.eightDp))
            StatCard(
                icon = Icons.Filled.Visibility,
                label = stringResource(R.string.visibility),
                value = state.visibilityMi?.let { "$it mi" } ?: "—",
                subText = state.visibilityNote.takeIf { it.isNotEmpty() },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
