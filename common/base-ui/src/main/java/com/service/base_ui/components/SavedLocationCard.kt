package com.service.base_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.service.base_ui.R
import com.service.utils.ui.LocalSpacing
import com.service.utils.ui.LocalTextSize

@Composable
fun SavedLocationCard(
    city: String,
    country: String,
    temp: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    isCurrentLocation: Boolean = false,
    onClick: () -> Unit = {},
    onRemove: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    val sizes = LocalTextSize.current
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        shape = RoundedCornerShape(spacing.twentyDp),
        border = if (isCurrentLocation) BorderStroke(spacing.oneDp, MaterialTheme.colors.primary) else null,
        modifier = modifier
            .fillMaxWidth()
            .height(spacing.hundredDp + spacing.twentyDp)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onRemove() },
            )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(spacing.twentyDp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF101A33),
                                MaterialTheme.colors.surface
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = spacing.twentyDp, vertical = spacing.sixteenDp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (isCurrentLocation) {
                        Text(
                            text = stringResource(R.string.current_location),
                            color = MaterialTheme.colors.primary,
                            fontSize = sizes.elevenSp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(Modifier.height(spacing.fourDp))
                    }
                    Text(
                        text = city,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(spacing.fourDp))
                    Text(
                        text = country,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondary
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(spacing.twentyEightDp)
                    )
                    Spacer(Modifier.height(spacing.fourDp))
                    Text(
                        text = temp,
                        fontSize = sizes.twentyEightSp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }

        }
    }
}
