package com.service.base_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SavedLocationCard(
    city: String,
    country: String,
    temp: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    isEditing: Boolean = false,
    onClick: () -> Unit = {},
    onRemove: () -> Unit = {},
) {
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(enabled = !isEditing) { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
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
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = city,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(4.dp))
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
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = temp,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
            if (isEditing) {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Remove",
                        tint = MaterialTheme.colors.error
                    )
                }
            }
        }
    }
}
