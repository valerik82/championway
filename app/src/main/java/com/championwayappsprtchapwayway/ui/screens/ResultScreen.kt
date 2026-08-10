package com.championwayappsprtchapwayway.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.championwayappsprtchapwayway.ui.components.AppCard
import com.championwayappsprtchapwayway.ui.components.PrimaryButton
import com.championwayappsprtchapwayway.ui.theme.Accent
import com.championwayappsprtchapwayway.ui.theme.TextMuted

@Composable
fun ResultScreen(
    score: Int,
    message: String,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "You scored",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = score.toString(),
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Accent,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = "Play again",
                onClick = onPlayAgain,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
