package com.service.splash_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.service.base_ui.R
import com.service.utils.ui.LocalSpacing
import com.service.utils.ui.LocalTextSize

@Composable
fun SplashScreen(
    state: SplashContract.State,
    onDialogConfirm: () -> Unit = {},
    onDialogDismiss: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    val sizes = LocalTextSize.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                modifier = Modifier.size(spacing.fortyEightDp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
            )
            Spacer(Modifier.height(spacing.sixteenDp))
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = sizes.twentyFourSp,
            )
            Spacer(Modifier.height(spacing.twentyDp))
            if (state.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
        }

        if (state.showPermissionDialog) {
            AlertDialog(
                onDismissRequest = onDialogDismiss,
                title = { Text(text = stringResource(R.string.location_required)) },
                text = { Text(text = stringResource(R.string.location_permission_required)) },
                confirmButton = {
                    TextButton(onClick = onDialogConfirm) { Text(text = stringResource(R.string.yes)) }
                },
                dismissButton = {
                    TextButton(onClick = onDialogDismiss) { Text(text = stringResource(R.string.no)) }
                }
            )
        }
    }
}