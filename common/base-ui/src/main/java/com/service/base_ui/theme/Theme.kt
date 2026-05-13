package com.service.base_ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.service.utils.ui.LocalSpacing
import com.service.utils.ui.LocalTextSize
import com.service.utils.ui.Spacing
import com.service.utils.ui.TextSize

private val DarkColorPalette = darkColors(
    primary = Color(0xFF60A5FA),        // Sky Blue
    secondary = Color(0xFF94A3B8),       // Cool Gray
    background = Color(0xFF060E20),      // Deep Midnight
    surface = Color(0xFF0B1326),         // Dark Navy
    error = Color(0xFFF87171),           // Soft Red
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFFFFFFFF),
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF2563EB),         // Vivid Blue
    secondary = Color(0xFF475569),        // Slate
    background = Color(0xFFF8FAFC),       // Off-white
    surface = Color(0xFFFFFFFF),          // White
    error = Color(0xFFDC2626),            // Red
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    onBackground = Color(0xFF0F172A),     // Dark Navy text
    onSurface = Color(0xFF1E293B),        // Slate text
    onError = Color(0xFFFFFFFF),

    )


@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalTextSize provides TextSize(),
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }

}

@Preview
@Composable
private fun WeatherThemeDarkPreview() {
    WeatherTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(16.dp),
        ) {
            Text(text = "Dark theme sample", color = MaterialTheme.colors.onBackground)
        }
    }
}

@Preview
@Composable
private fun WeatherThemeLightPreview() {
    WeatherTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(16.dp),
        ) {
            Text(text = "Light theme sample", color = MaterialTheme.colors.onBackground)
        }
    }
}