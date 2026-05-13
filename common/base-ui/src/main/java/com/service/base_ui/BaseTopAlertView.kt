package com.service.base_ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.service.base_ui.theme.WeatherTheme
import com.service.utils.ui.LocalSpacing
import kotlinx.coroutines.delay

@Composable
fun BaseTopAlertView(
    isVisible: Boolean,
    message: String,
    isErrorAlert: Boolean,
    autoDismissMillis: Long? = 4000L,
    onTimerEnd: () -> Unit = {},
) {
    val errorColor = MaterialTheme.colors.error
    val successColor = Color(0xFF22C55E)
    val borderStroke = if (isErrorAlert) errorColor else successColor
    val backGroundColor = if (isErrorAlert) {
        errorColor.copy(alpha = 0.12f)
    } else {
        successColor.copy(alpha = 0.12f)
    }
    val icon = if (isErrorAlert) Icons.Filled.Warning else Icons.Filled.CheckCircle
    val spacing = LocalSpacing.current

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(),
        exit = slideOutVertically(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = spacing.threeDp,
                    bottom = spacing.threeDp,
                    start = spacing.twelveDp,
                    end = spacing.twelveDp
                )
                .clip(RoundedCornerShape(spacing.fourDp))
                .background(color = backGroundColor)
                .border(BorderStroke(spacing.oneDp, borderStroke)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(start = spacing.tenDp),
                tint = borderStroke
            )
            Text(
                text = message,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(
                    start = spacing.tenDp,
                    end = spacing.tenDp,
                    top = spacing.elevenDp,
                    bottom = spacing.elevenDp
                )
            )
        }
    }
    LaunchedEffect(isVisible) {
        if (isVisible && autoDismissMillis != null) {
            delay(autoDismissMillis)
            onTimerEnd.invoke()
        }
    }
}

@Preview
@Composable
private fun BaseTopAlertViewErrorPreview() {
    WeatherTheme {
        BaseTopAlertView(
            isVisible = true,
            message = "No internet connection",
            isErrorAlert = true,
            autoDismissMillis = null,
        )
    }
}

@Preview
@Composable
private fun BaseTopAlertViewSuccessPreview() {
    WeatherTheme {
        BaseTopAlertView(
            isVisible = true,
            message = "Connection restored",
            isErrorAlert = false,
            autoDismissMillis = null,
        )
    }
}
