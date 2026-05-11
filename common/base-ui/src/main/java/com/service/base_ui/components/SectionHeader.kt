package com.service.base_ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.service.utils.ui.LocalTextSize

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit)? = null,
) {
    val sizes = LocalTextSize.current
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title.uppercase(),
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.SemiBold,
            fontSize = sizes.elevenSp,
            letterSpacing = 1.sp,
            modifier = Modifier.weight(1f)
        )
        trailing?.invoke()
    }
}
