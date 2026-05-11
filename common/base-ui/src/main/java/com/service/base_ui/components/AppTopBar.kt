package com.service.base_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.service.utils.ui.LocalSpacing

@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = null,
    trailingDescription: String? = null,
    onTrailingClick: (() -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    Surface(
        color = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(spacing.fiftySixDp)
                .padding(horizontal = spacing.sixteenDp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(spacing.tenDp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onBackground
            )
            if (trailingIcon != null) {
                IconButton(onClick = { onTrailingClick?.invoke() }) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = trailingDescription,
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            } else {
                Box(modifier = Modifier.size(spacing.fortyDp))
            }
        }
    }
    Divider(color = Color.White.copy(alpha = 0.06f), thickness = spacing.oneDp)
}
