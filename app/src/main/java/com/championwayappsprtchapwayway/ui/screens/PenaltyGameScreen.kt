package com.championwayappsprtchapwayway.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.championwayappsprtchapwayway.ui.components.PrimaryButton
import com.championwayappsprtchapwayway.ui.components.SecondaryButton
import com.championwayappsprtchapwayway.ui.theme.Accent
import com.championwayappsprtchapwayway.ui.theme.BgCard
import com.championwayappsprtchapwayway.ui.theme.Correct
import com.championwayappsprtchapwayway.ui.theme.DarkNavy
import com.championwayappsprtchapwayway.ui.theme.KeeperTop
import com.championwayappsprtchapwayway.ui.theme.PitchBottom
import com.championwayappsprtchapwayway.ui.theme.PitchMid
import com.championwayappsprtchapwayway.ui.theme.PitchTop
import com.championwayappsprtchapwayway.ui.theme.TextMuted
import com.championwayappsprtchapwayway.ui.theme.Wrong
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

private const val PENALTY_ROUNDS = 5
private val ZONE_LABELS = listOf("LEFT", "MID", "RIGHT")

@Composable
fun PenaltyGameScreen(
    onBackToMenu: () -> Unit,
    modifier: Modifier = Modifier,
    resetKey: Int = 0,
) {
    var round by remember(resetKey) { mutableIntStateOf(0) }
    var goals by remember(resetKey) { mutableIntStateOf(0) }
    var busy by remember(resetKey) { mutableStateOf(false) }
    var showEnd by remember(resetKey) { mutableStateOf(false) }
    var message by remember(resetKey) { mutableStateOf("Tap a zone to shoot") }
    var messageColor by remember(resetKey) { mutableStateOf(Color.Unspecified) }

    val ballOffsetX = remember(resetKey) { Animatable(0f) }
    val ballOffsetY = remember(resetKey) { Animatable(0f) }
    val ballScale = remember(resetKey) { Animatable(1f) }
    val ballAlpha = remember(resetKey) { Animatable(1f) }
    val keeperOffsetX = remember(resetKey) { Animatable(0f) }

    val scope = rememberCoroutineScope()

    fun resetVisuals() {
        scope.launch {
            ballOffsetX.snapTo(0f)
            ballOffsetY.snapTo(0f)
            ballScale.snapTo(1f)
            ballAlpha.snapTo(1f)
            keeperOffsetX.snapTo(0f)
        }
    }

    fun startNewGame() {
        round = 0
        goals = 0
        busy = false
        showEnd = false
        message = "Tap a zone to shoot"
        messageColor = Color.Unspecified
        resetVisuals()
    }

    LaunchedEffect(resetKey) {
        startNewGame()
    }

    fun onZoneShoot(dir: Int, pitchHeightPx: Float) {
        if (busy || round >= PENALTY_ROUNDS) return
        busy = true
        messageColor = Color.Unspecified

        val keeperDir = Random.nextInt(3)
        val saved = keeperDir == dir
        val keeperTarget = when (keeperDir) {
            0 -> -pitchHeightPx * 0.22f
            2 -> pitchHeightPx * 0.22f
            else -> 0f
        }
        val ballTargetX = when (dir) {
            0 -> -pitchHeightPx * 0.21f
            2 -> pitchHeightPx * 0.21f
            else -> 0f
        }
        val ballTargetY = if (dir == 1) -pitchHeightPx * 0.72f else -pitchHeightPx * 0.66f

        scope.launch {
            launch {
                keeperOffsetX.animateTo(keeperTarget, tween(480))
            }
            launch {
                ballOffsetX.animateTo(ballTargetX, tween(520))
                ballOffsetY.animateTo(ballTargetY, tween(520))
                ballScale.animateTo(0.88f, tween(520))
            }
            delay(520)
            if (saved) {
                ballAlpha.animateTo(0.35f, tween(350))
            }
            message = if (saved) "Saved!" else "Goal!"
            messageColor = if (saved) Wrong else Correct
            if (!saved) goals += 1
            round += 1
            delay(650)
            if (round >= PENALTY_ROUNDS) {
                showEnd = true
                busy = true
            } else {
                resetVisuals()
                message = "Tap a zone to shoot"
                messageColor = Color.Unspecified
                busy = false
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PitchTop, PitchMid, PitchBottom),
                ),
            ),
    ) {
        if (showEnd) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xCC07070A))
                    .systemBarsPadding()
                    .padding(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                PenaltyEndPanel(
                    goals = goals,
                    onPlayAgain = { startNewGame() },
                    onHome = onBackToMenu,
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
            ) {
                GameHud(
                    round = round,
                    goals = goals,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                )

                BoxWithConstraints(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                ) {
                    val pitchHeightPx = maxHeight.value

                    Column(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth(0.92f)
                            .padding(top = 8.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(96.dp)
                                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                                .border(
                                    width = 3.dp,
                                    color = Color.White.copy(alpha = 0.9f),
                                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                                )
                                .background(Color(0x590A1628)),
                        ) {
                            ZONE_LABELS.forEachIndexed { index, label ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(Color.White.copy(alpha = 0.05f))
                                        .then(
                                            if (index < 2) {
                                                Modifier.border(
                                                    width = 1.dp,
                                                    color = Color.White.copy(alpha = 0.18f),
                                                )
                                            } else {
                                                Modifier
                                            },
                                        )
                                        .clickable(enabled = !busy) {
                                            onZoneShoot(index, pitchHeightPx)
                                        },
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = label,
                                        color = Color.White.copy(alpha = 0.55f),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .offset { IntOffset(keeperOffsetX.value.roundToInt(), 0) }
                                    .size(width = 38.dp, height = 48.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 8.dp,
                                            bottomStart = 5.dp,
                                            bottomEnd = 5.dp,
                                        ),
                                    )
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(KeeperTop, Accent),
                                        ),
                                    ),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(top = 8.dp)
                                        .size(width = 22.dp, height = 18.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(DarkNavy),
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = maxHeight * 0.08f)
                            .offset {
                                IntOffset(
                                    ballOffsetX.value.roundToInt(),
                                    ballOffsetY.value.roundToInt(),
                                )
                            }
                            .size((30 * ballScale.value).dp)
                            .alpha(ballAlpha.value)
                            .clip(RoundedCornerShape(50))
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(Color.White, Color(0xFFC5C5C5)),
                                ),
                            ),
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xE607070A))
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = message,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (messageColor == Color.Unspecified) {
                            Color.White
                        } else {
                            messageColor
                        },
                    )

                    TextButton(onClick = onBackToMenu) {
                        Text(
                            text = "← Back to menu",
                            color = TextMuted,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GameHud(
    round: Int,
    goals: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            Text(
                text = "Shot ",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = "${round.coerceAtMost(PENALTY_ROUNDS - 1) + 1}",
                color = Accent,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = " / $PENALTY_ROUNDS",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Row {
            Text(
                text = "Goals: ",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = goals.toString(),
                color = Accent,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun PenaltyEndPanel(
    goals: Int,
    onPlayAgain: () -> Unit,
    onHome: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(BgCard)
            .padding(24.dp),
    ) {
        Text(
            text = "Penalty shootout",
            color = TextMuted,
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = goals.toString(),
            fontSize = MaterialTheme.typography.displayLarge.fontSize,
            fontWeight = FontWeight.ExtraBold,
            color = Accent,
        )

        Text(
            text = if (goals == PENALTY_ROUNDS) {
                "Perfect — all five found the net!"
            } else {
                "$goals goal${if (goals == 1) "" else "s"} from $PENALTY_ROUNDS shots"
            },
            color = TextMuted,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            text = "Play again",
            onClick = onPlayAgain,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        SecondaryButton(
            text = "Home",
            onClick = onHome,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
