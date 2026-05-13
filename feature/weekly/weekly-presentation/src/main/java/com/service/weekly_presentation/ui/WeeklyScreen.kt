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
import androidx.compose.ui.tooling.preview.Preview
import com.service.base_ui.R
import com.service.base_ui.ScaffoldSnackFree
import com.service.base_ui.components.AppTopBar
import com.service.base_ui.components.ErrorState
import com.service.base_ui.components.ForecastDayRow
import com.service.base_ui.components.LoadingState
import com.service.base_ui.theme.WeatherTheme
import com.service.utils.ui.LocalSpacing
import com.service.utils.ui.LocalTextSize
import com.service.utils.weather.WeatherCodeMapper

@Composable
internal fun WeeklyScreen(
    state: WeeklyContract.State,
    onEventSent: (event: WeeklyContract.Event) -> Unit,
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.sixteenDp),
        verticalArrangement = Arrangement.spacedBy(spacing.fourDp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.sixteenDp, bottom = spacing.sixteenDp),
            verticalArrangement = Arrangement.spacedBy(spacing.twelveDp),
            contentPadding = PaddingValues(bottom = spacing.sixteenDp),
        ) {
            item { WeeklyTitleInf(state.isCurrentLocation, state.cityLabel, state.dateRange) }
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
            item { Spacer(Modifier.height(spacing.fortyDp)) }
        }
    }
}

@Composable
fun WeeklyTitleInf(isCurrent: Boolean = false, cityLabel: String, dateRange: String) {
    val spacing = LocalSpacing.current
    val sizes = LocalTextSize.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.sixteenDp),
        verticalArrangement = Arrangement.spacedBy(spacing.fourDp)
    ) {
        Spacer(Modifier.height(spacing.eightDp))
        if(isCurrent){
            Text(
                text = stringResource(R.string.current_location).uppercase(),
                color = MaterialTheme.colors.primary,
                fontSize = sizes.elevenSp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Text(
            text = cityLabel,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.SemiBold,
            fontSize = sizes.twentyTwoSp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
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
                text = dateRange,
                color = MaterialTheme.colors.secondary,
                fontSize = sizes.thirteenSp,
            )
        }
    }
}

private fun sampleWeeklyState() = WeeklyContract.State(
    isLoading = false,
    cityLabel = "London",
    isCurrentLocation = true,
    dateRange = "Jun 3 – Jun 9",
    days = listOf(
        WeeklyContract.DayRow("Monday", "Jun 3", 2, true, 30, 24, 14, isToday = true),
        WeeklyContract.DayRow("Tuesday", "Jun 4", 1, true, 10, 26, 15, isToday = false),
        WeeklyContract.DayRow("Wednesday", "Jun 5", 3, true, 60, 22, 13, isToday = false),
        WeeklyContract.DayRow("Thursday", "Jun 6", 61, true, 80, 19, 12, isToday = false),
        WeeklyContract.DayRow("Friday", "Jun 7", 0, true, 5, 27, 16, isToday = false),
        WeeklyContract.DayRow("Saturday", "Jun 8", 2, true, 20, 28, 17, isToday = false),
        WeeklyContract.DayRow("Sunday", "Jun 9", 1, true, 15, 26, 16, isToday = false),
    ),
)

@Preview
@Composable
private fun WeeklyScreenLoadingPreview() {
    WeatherTheme {
        WeeklyScreen(
            state = WeeklyContract.State(isLoading = true),
            onEventSent = {},
        )
    }
}

@Preview
@Composable
private fun WeeklyScreenErrorPreview() {
    WeatherTheme {
        WeeklyScreen(
            state = WeeklyContract.State(isLoading = false, errorMessage = "Unable to load forecast"),
            onEventSent = {},
        )
    }
}

@Preview
@Composable
private fun WeeklyScreenContentPreview() {
    WeatherTheme {
        WeeklyScreen(
            state = sampleWeeklyState(),
            onEventSent = {},
        )
    }
}

@Preview
@Composable
private fun WeeklyTitleInfPreview() {
    WeatherTheme {
        WeeklyTitleInf(isCurrent = true, cityLabel = "London", dateRange = "Jun 3 – Jun 9")
    }
}
