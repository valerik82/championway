package com.championwayappsprtchapwayway.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.championwayappsprtchapwayway.R
import com.championwayappsprtchapwayway.ui.theme.BgDeep
import com.championwayappsprtchapwayway.ui.theme.TextMuted
import kotlinx.coroutines.delay

private const val LOADING_DURATION_MS = 8_000L

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(LOADING_DURATION_MS)
        onFinished()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "splash")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1650, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "ball-rotation",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF020203),
                        BgDeep,
                        Color(0xFF07070C),
                    ),
                ),
            )
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0x38FF2D2D),
                        Color.Transparent,
                    ),
                    radius = 700f,
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(120.dp, 40.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0x59FF2D2D),
                            Color.Transparent,
                        ),
                    ),
                )
                .align(Alignment.Center),
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.ball),
                contentDescription = null,
                modifier = Modifier
                    .size(104.dp)
                    .rotate(rotation),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Loading...",
                style = MaterialTheme.typography.titleMedium,
                color = TextMuted,
            )
        }
    }
}
