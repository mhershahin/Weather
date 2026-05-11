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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth().height(78.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 12.dp)
                    .width(if (isToday) 4.dp else 0.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colors.primary)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                if (isToday) {
                    Text(
                        text = "TODAY",
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp
                    )
                    Spacer(Modifier.height(2.dp))
                }
                Text(
                    text = day,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = date,
                    color = MaterialTheme.colors.secondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$precipPct%",
                    color = if (precipPct >= 50) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(
                    text = "${tempMax}°",
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "${tempMin}°",
                    color = MaterialTheme.colors.secondary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            }
        }
    }
}
