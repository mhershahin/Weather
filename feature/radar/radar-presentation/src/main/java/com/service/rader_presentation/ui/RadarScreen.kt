package com.service.rader_presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.service.base_ui.R
import com.service.base_ui.ScaffoldSnackFree
import com.service.base_ui.components.AppTopBar
import com.service.base_ui.components.SavedLocationCard
import com.service.base_ui.components.SearchField
import com.service.base_ui.components.SectionHeader
import com.service.entity.domain.Location
import com.service.utils.ui.LocalSpacing
import com.service.utils.ui.LocalTextSize
import com.service.utils.weather.WeatherCodeMapper
import kotlinx.coroutines.flow.Flow

@Composable
internal fun RadarScreen(
    state: RadarContract.State,
    effectFlow: Flow<RadarContract.Effect>?,
    onEventSent: (event: RadarContract.Event) -> Unit,
) {
    ScaffoldSnackFree(backgroundColor = MaterialTheme.colors.background) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
            ) {
                AppTopBar(
                    title = stringResource(R.string.radar),
                    trailingIcon = if (state.isSearchOpen) null else Icons.Filled.Search,
                    trailingDescription = stringResource(R.string.search),
                    onTrailingClick = { onEventSent(RadarContract.Event.OpenSearch) },
                )
                if (state.isSearchOpen) {
                    SearchSection(
                        state = state,
                        onQueryChange = { onEventSent(RadarContract.Event.SearchQueryChanged(it)) },
                        onSelect = { onEventSent(RadarContract.Event.AddCity(it)) },
                        onClose = { onEventSent(RadarContract.Event.CloseSearch) },
                    )
                } else {
                    SavedSection(
                        state = state,
                        onAddCity = { onEventSent(RadarContract.Event.OpenSearch) },
                        onRemove = { onEventSent(RadarContract.Event.RemoveCity(it)) },
                        onCityClick = { onEventSent(RadarContract.Event.CityClicked(it)) },
                    )
                }
            }
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {},
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}

@Composable
private fun SavedSection(
    state: RadarContract.State,
    onAddCity: () -> Unit,
    onRemove: (Int) -> Unit,
    onCityClick: (Location) -> Unit,
) {
    val spacing = LocalSpacing.current
    val sizes = LocalTextSize.current
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = spacing.sixteenDp, vertical = spacing.twelveDp),
        verticalArrangement = Arrangement.spacedBy(spacing.twelveDp),
    ) {
        item {
            SectionHeader(title = stringResource(R.string.saved_locations))
        }
        items(state.saved, key = { "saved-${it.location.id}" }) { city ->
            val isGpsCity = state.gpsLocationId == city.location.id
            SavedLocationCard(
                city = city.location.name,
                country = city.location.country,
                temp = city.tempC?.let { "${it}°" } ?: "—",
                icon = WeatherCodeMapper.icon(city.weatherCode, city.isDay),
                isCurrentLocation = isGpsCity,
                onClick = { onCityClick(city.location) },
                onRemove = { onRemove(city.location.id) },
            )
        }
        item { AddCityButton(onClick = onAddCity) }
        item {
            Spacer(Modifier.height(spacing.fourDp))
            Text(
                text = stringResource(R.string.track_global_weather),
                color = MaterialTheme.colors.secondary,
                fontSize = sizes.elevenSp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(spacing.sixtyFourDp))
        }
    }
}

@Composable
private fun AddCityButton(onClick: () -> Unit) {
    val spacing = LocalSpacing.current
    Surface(
        color = Color.Transparent,
        shape = RoundedCornerShape(spacing.twentyDp),
        border = BorderStroke(spacing.oneDp, MaterialTheme.colors.secondary.copy(alpha = 0.4f)),
        modifier = Modifier
            .fillMaxWidth()
            .height(spacing.fiftySixDp)
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground,
            )
            Spacer(Modifier.size(spacing.eightDp))
            Text(
                text = stringResource(R.string.add_city),
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun SearchSection(
    state: RadarContract.State,
    onQueryChange: (String) -> Unit,
    onSelect: (Location) -> Unit,
    onClose: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.sixteenDp, vertical = spacing.twelveDp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(1f)) {
                SearchField(
                    query = state.searchQuery,
                    onQueryChange = onQueryChange,
                    placeholder = stringResource(R.string.search_hint),
                )
            }
            TextButton(onClick = onClose) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        Spacer(Modifier.height(spacing.twelveDp))
        if (state.searchResults.isEmpty() && state.searchQuery.length >= 2 && !state.isSearching) {
            Text(
                text = "No matches",
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.fillMaxWidth().padding(top = spacing.twentyFourDp),
                textAlign = TextAlign.Center,
            )
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.searchResults, key = { it.id }) { loc ->
                SearchResultRow(loc = loc, onClick = { onSelect(loc) })
                Divider(color = Color.White.copy(alpha = 0.05f))
            }
        }
    }
}

@Composable
private fun SearchResultRow(loc: Location, onClick: () -> Unit) {
    val spacing = LocalSpacing.current
    val sizes = LocalTextSize.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = spacing.fourDp, vertical = spacing.fourteenDp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier.size(spacing.eighteenDp),
        )
        Spacer(Modifier.size(spacing.twelveDp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = loc.name,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.SemiBold,
            )
            val sub = listOfNotNull(loc.region.ifEmpty { null }, loc.country.ifEmpty { null }).joinToString(", ")
            if (sub.isNotEmpty()) {
                Text(
                    text = sub,
                    color = MaterialTheme.colors.secondary,
                    fontSize = sizes.twelveSp,
                )
            }
        }
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(spacing.twentyEightDp)
                .clip(RoundedCornerShape(spacing.fourteenDp))
                .border(spacing.oneDp, MaterialTheme.colors.primary.copy(alpha = 0.4f), RoundedCornerShape(spacing.fourteenDp))
                .padding(spacing.fourDp),
        )
    }
}
