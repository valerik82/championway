package com.championwayappsprtchapwayway.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.championwayappsprtchapwayway.ui.theme.BgCard
import com.championwayappsprtchapwayway.ui.theme.BgDeep
import com.championwayappsprtchapwayway.ui.theme.ButtonGreen
import com.championwayappsprtchapwayway.ui.theme.LoadingBgBottom
import com.championwayappsprtchapwayway.ui.theme.LoadingBgTop
import com.championwayappsprtchapwayway.ui.theme.PitchBottom
import com.championwayappsprtchapwayway.ui.theme.PitchMid

@Composable
fun LoadingScreenBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        LoadingBgTop,
                        PitchBottom,
                        PitchMid,
                        LoadingBgBottom,
                    ),
                ),
            ),
    ) {
        content()
    }
}

@Composable
fun AppBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BgDeep,
                        Color(0xFF050509),
                    ),
                ),
            )
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0x38FF2D2D),
                        Color.Transparent,
                    ),
                    radius = 900f,
                ),
            ),
    ) {
        content()
    }
}

@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    scrollable: Boolean = false,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (scrollable) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .widthIn(max = 520.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                content()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 520.dp),
            ) {
                content()
            }
        }
    }
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(BgCard)
            .border(
                width = 1.dp,
                color = Color(0x29FF2D2D),
                shape = RoundedCornerShape(14.dp),
            )
            .padding(horizontal = 18.dp, vertical = 20.dp),
    ) {
        content()
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = ButtonGreen,
            contentColor = Color(0xFF0A1628),
            disabledContainerColor = ButtonGreen.copy(alpha = 0.45f),
            disabledContentColor = Color(0xFF0A1628).copy(alpha = 0.7f),
        ),
    ) {
        androidx.compose.material3.Text(
            text = text,
            style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, ButtonGreen),
        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
            contentColor = ButtonGreen,
        ),
    ) {
        androidx.compose.material3.Text(
            text = text,
            style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
        )
    }
}
