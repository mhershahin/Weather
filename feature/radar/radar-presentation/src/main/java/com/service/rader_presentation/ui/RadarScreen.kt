package com.service.rader_presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.service.base_ui.ScaffoldSnackFree
import kotlinx.coroutines.flow.Flow

@Composable
internal fun RadarScreen(
    state: RadarContract.State,
    effectFlow: Flow<RadarContract.Effect>?,
    onEventSent: (event: RadarContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: RadarContract.Effect.Navigation) -> Unit,
) {
    ScaffoldSnackFree {
        Box(modifier = Modifier.padding(it)) {

            Text(text = "Radar")
        }
    }
}