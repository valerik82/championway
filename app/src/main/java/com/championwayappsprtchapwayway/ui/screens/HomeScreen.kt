package com.championwayappsprtchapwayway.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.championwayappsprtchapwayway.R
import com.championwayappsprtchapwayway.ui.components.PrimaryButton
import com.championwayappsprtchapwayway.ui.components.SecondaryButton
import com.championwayappsprtchapwayway.ui.theme.BgCard
import com.championwayappsprtchapwayway.ui.theme.TextMuted
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen(
    onStartQuiz: () -> Unit,
    onOpenGame: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(BgCard)
            .border(
                width = 1.dp,
                color = Color(0x29FF2D2D),
                shape = RoundedCornerShape(14.dp),
            ),
    ) {
        Image(
            painter = painterResource(R.drawable.main_bann),
            contentDescription = "Sport quiz banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(topStart = 13.dp, topEnd = 13.dp)),
        )

        Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 20.dp)) {
            Text(
                text = "Test your knowledge across football, basketball, tennis, Olympics, and more. Each question has one correct answer.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = "Start quiz",
                onClick = onStartQuiz,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(10.dp))

            SecondaryButton(
                text = "Penalty kicks",
                onClick = onOpenGame,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
