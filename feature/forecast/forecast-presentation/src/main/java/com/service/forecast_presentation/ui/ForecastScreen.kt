package com.service.forecast_presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.service.base_ui.ScaffoldSnackFree
import kotlinx.coroutines.flow.Flow

@Composable
internal fun ForecastScreen(
    state: ForecastContract.State,
    effectFlow: Flow<ForecastContract.Effect>?,
    onEventSent: (event: ForecastContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: ForecastContract.Effect.Navigation) -> Unit,
) {
    ScaffoldSnackFree {
        Box(modifier = Modifier.padding(it)) {

            Text(text = "Forecast")
        }
    }
}