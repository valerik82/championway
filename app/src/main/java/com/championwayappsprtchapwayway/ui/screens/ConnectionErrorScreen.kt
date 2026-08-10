package com.championwayappsprtchapwayway.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.championwayappsprtchapwayway.R
import com.championwayappsprtchapwayway.ui.components.AppCard
import com.championwayappsprtchapwayway.ui.components.PrimaryButton
import com.championwayappsprtchapwayway.ui.components.ScreenContainer
import com.championwayappsprtchapwayway.ui.theme.TextMuted

@Composable
fun ConnectionErrorScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ScreenContainer(modifier = modifier.fillMaxSize()) {
        AppCard {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(R.string.connection_error_title),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.connection_error_message),
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = stringResource(R.string.connection_error_retry),
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
