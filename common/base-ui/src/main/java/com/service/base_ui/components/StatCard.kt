package com.service.base_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.service.utils.ui.LocalSpacing
import com.service.utils.ui.LocalTextSize

@Composable
fun StatCard(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    subText: String? = null,
    progress: Float? = null,
) {
    val spacing = LocalSpacing.current
    val sizes = LocalTextSize.current
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        shape = RoundedCornerShape(spacing.sixteenDp),
        modifier = modifier.heightIn(min = spacing.hundredDp + spacing.twentyDp)
    ) {
        Column(modifier = Modifier.padding(spacing.sixteenDp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier.size(spacing.sixteenDp)
                )
                Spacer(Modifier.width(spacing.sixDp))
                Text(
                    text = label.uppercase(),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.secondary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = sizes.twelveSp
                )
            }
            Spacer(Modifier.height(spacing.eightDp))
            Text(
                text = value,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            if (subText != null) {
                Spacer(Modifier.height(spacing.sixDp))
                Text(
                    text = subText,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary
                )
            }
            if (progress != null) {
                Spacer(Modifier.height(spacing.tenDp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(spacing.fourDp)
                        .clip(RoundedCornerShape(spacing.twoDp))
                        .background(Color.White.copy(alpha = 0.12f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress.coerceIn(0f, 1f))
                            .height(spacing.fourDp)
                            .clip(RoundedCornerShape(spacing.twoDp))
                            .background(MaterialTheme.colors.primary)
                    )
                }
            }
        }
    }
}
