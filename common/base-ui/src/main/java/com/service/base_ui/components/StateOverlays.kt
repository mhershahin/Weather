package com.service.base_ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.service.base_ui.theme.WeatherTheme
import com.service.utils.ui.LocalSpacing

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}

@Composable
fun ErrorState(message: String, modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier.fillMaxSize().padding(spacing.twentyFourDp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = message,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1
            )
            Spacer(Modifier.height(spacing.eightDp))
        }
    }
}

@Preview
@Composable
private fun LoadingStatePreview() {
    WeatherTheme { LoadingState() }
}

@Preview
@Composable
private fun ErrorStatePreview() {
    WeatherTheme { ErrorState(message = "Something went wrong while fetching weather.") }
}