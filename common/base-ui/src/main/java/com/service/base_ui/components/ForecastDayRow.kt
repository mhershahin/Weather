package com.service.base_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.service.base_ui.theme.WeatherTheme
import com.service.utils.ui.LocalSpacing
import com.service.utils.ui.LocalTextSize

@Composable
fun ForecastDayRow(
    day: String,
    date: String,
    icon: ImageVector,
    precipPct: Int,
    tempMax: Int,
    tempMin: Int,
    modifier: Modifier = Modifier,
    isToday: Boolean = false,
) {
    val spacing = LocalSpacing.current
    val sizes = LocalTextSize.current
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        shape = RoundedCornerShape(spacing.sixteenDp),
        modifier = modifier.fillMaxWidth().height(spacing.seventyFourDp + spacing.fourDp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = spacing.twelveDp)
                    .width(if (isToday) spacing.fourDp else spacing.zeroDp)
                    .clip(RoundedCornerShape(spacing.twoDp))
                    .background(MaterialTheme.colors.primary)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = spacing.sixteenDp)
            ) {
                if (isToday) {
                    Text(
                        text = "TODAY",
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = sizes.elevenSp
                    )
                    Spacer(Modifier.height(spacing.twoDp))
                }
                Text(
                    text = day,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = sizes.sixteenSp
                )
                Spacer(Modifier.height(spacing.twoDp))
                Text(
                    text = date,
                    color = MaterialTheme.colors.secondary,
                    fontSize = sizes.elevenSp,
                    fontWeight = FontWeight.Medium
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = spacing.eightDp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(spacing.twentyEightDp)
                )
                Spacer(Modifier.height(spacing.fourDp))
                Text(
                    text = "$precipPct%",
                    color = if (precipPct >= 50) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
                    fontSize = sizes.elevenSp,
                    fontWeight = FontWeight.Medium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = spacing.sixteenDp)
            ) {
                Text(
                    text = "${tempMax}°",
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = sizes.eighteenSp
                )
                Spacer(Modifier.width(spacing.eightDp))
                Text(
                    text = "${tempMin}°",
                    color = MaterialTheme.colors.secondary,
                    fontWeight = FontWeight.Medium,
                    fontSize = sizes.eighteenSp
                )
            }
        }
    }
}

@Preview
@Composable
private fun ForecastDayRowTodayPreview() {
    WeatherTheme {
        ForecastDayRow(
            day = "Monday",
            date = "Jun 3",
            icon = Icons.Filled.Cloud,
            precipPct = 70,
            tempMax = 26,
            tempMin = 15,
            isToday = true,
        )
    }
}

@Preview
@Composable
private fun ForecastDayRowPreview() {
    WeatherTheme {
        ForecastDayRow(
            day = "Tuesday",
            date = "Jun 4",
            icon = Icons.Filled.Cloud,
            precipPct = 20,
            tempMax = 28,
            tempMin = 17,
        )
    }
}
