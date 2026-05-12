package com.service.weekly_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.service.base_ui.R
import com.service.base_ui.ScaffoldSnackFree
import com.service.base_ui.components.AppTopBar
import com.service.base_ui.components.ErrorState
import com.service.base_ui.components.ForecastDayRow
import com.service.base_ui.components.LoadingState
import com.service.utils.ui.LocalSpacing
import com.service.utils.ui.LocalTextSize
import com.service.utils.weather.WeatherCodeMapper
import kotlinx.coroutines.flow.Flow

@Composable
internal fun WeeklyScreen(
    state: WeeklyContract.State,
    effectFlow: Flow<WeeklyContract.Effect>?,
    onEventSent: (event: WeeklyContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: WeeklyContract.Effect.Navigation) -> Unit,
) {
    ScaffoldSnackFree(backgroundColor = MaterialTheme.colors.background) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            AppTopBar(title = stringResource(R.string.weekly))
            when {
                state.isLoading && state.days.isEmpty() -> LoadingState()
                state.errorMessage != null && state.days.isEmpty() ->
                    ErrorState(message = state.errorMessage)
                else -> WeeklyContent(state)
            }
        }
    }
}

@Composable
private fun WeeklyContent(state: WeeklyContract.State) {
    val spacing = LocalSpacing.current
    val sizes = LocalTextSize.current
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = spacing.sixteenDp)) {
        Spacer(Modifier.height(spacing.eightDp))
        Text(
            text = stringResource(R.string.current_location).uppercase(),
            color = MaterialTheme.colors.primary,
            fontSize = sizes.elevenSp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(spacing.twoDp))
        Text(
            text = state.cityLabel,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.SemiBold,
            fontSize = sizes.twentyTwoSp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(spacing.fourDp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier.size(spacing.fourteenDp),
            )
            Spacer(Modifier.size(spacing.sixDp))
            Text(
                text = state.dateRange,
                color = MaterialTheme.colors.secondary,
                fontSize = sizes.thirteenSp,
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(top = spacing.sixteenDp,bottom = spacing.sixteenDp),
            verticalArrangement = Arrangement.spacedBy(spacing.twelveDp),
            contentPadding = PaddingValues(bottom = spacing.sixteenDp),
        ) {
            items(state.days, key = { it.date + it.day }) { row ->
                ForecastDayRow(
                    day = row.day,
                    date = row.date,
                    icon = WeatherCodeMapper.icon(row.weatherCode, row.isDay),
                    precipPct = row.precipPct,
                    tempMax = row.tempMax,
                    tempMin = row.tempMin,
                    isToday = row.isToday,
                )
            }
        }
    }
}
