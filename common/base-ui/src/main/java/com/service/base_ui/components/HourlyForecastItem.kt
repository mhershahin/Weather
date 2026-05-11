package com.service.base_ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HourlyForecastItem(
    label: String,
    icon: ImageVector,
    temp: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(8.dp))
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = temp,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}
