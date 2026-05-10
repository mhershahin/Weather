package com.service.current_presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.service.base_ui.ScaffoldSnackFree
import kotlinx.coroutines.flow.Flow

@Composable
internal fun CurrentScreen(
    state: CurrentContract.State,
    effectFlow: Flow<CurrentContract.Effect>?,
    onEventSent: (event: CurrentContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: CurrentContract.Effect.Navigation) -> Unit,
) {
    ScaffoldSnackFree {
        Box(modifier = Modifier.padding(it)) {

            Text(text = "Current")
        }
    }
}