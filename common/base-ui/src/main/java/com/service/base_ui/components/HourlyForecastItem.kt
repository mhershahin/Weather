package com.service.base_ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.service.base_ui.theme.WeatherTheme
import com.service.utils.ui.LocalSpacing

@Composable
fun HourlyForecastItem(
    label: String,
    icon: ImageVector,
    temp: String,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier.width(spacing.seventyTwoDp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(spacing.eightDp))
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.size(spacing.twentyEightDp)
        )
        Spacer(Modifier.height(spacing.eightDp))
        Text(
            text = temp,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun HourlyForecastItemPreview() {
    WeatherTheme {
        HourlyForecastItem(label = "3 PM", icon = Icons.Filled.WbSunny, temp = "24°")
    }
}
