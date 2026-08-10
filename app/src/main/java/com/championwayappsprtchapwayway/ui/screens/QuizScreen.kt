package com.championwayappsprtchapwayway.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.championwayappsprtchapwayway.data.Question
import com.championwayappsprtchapwayway.ui.components.PrimaryButton
import com.championwayappsprtchapwayway.ui.theme.Accent
import com.championwayappsprtchapwayway.ui.theme.BgCard
import com.championwayappsprtchapwayway.ui.theme.BgDeep
import com.championwayappsprtchapwayway.ui.theme.BgElevated
import com.championwayappsprtchapwayway.ui.theme.Correct
import com.championwayappsprtchapwayway.ui.theme.TextMuted
import com.championwayappsprtchapwayway.ui.theme.Wrong

@Composable
fun QuizScreen(
    questionIndex: Int,
    totalQuestions: Int,
    score: Int,
    question: Question,
    answered: Boolean,
    selectedIndex: Int?,
    onPick: (Int) -> Unit,
    onNext: () -> Unit,
    onBackToMenu: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val progress = ((questionIndex + if (answered) 1 else 0).toFloat() / totalQuestions).coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BgDeep, Color(0xFF050509)),
                ),
            )
            .systemBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row {
                    Text(
                        text = "Question ",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextMuted,
                    )
                    Text(
                        text = "${questionIndex + 1}",
                        style = MaterialTheme.typography.labelLarge,
                        color = Accent,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = " / $totalQuestions",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextMuted,
                    )
                }
                Row {
                    Text(
                        text = "Score: ",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextMuted,
                    )
                    Text(
                        text = score.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        color = Accent,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(999.dp)),
                color = Accent,
                trackColor = BgElevated,
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = question.text,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.height(24.dp))

            question.choices.forEachIndexed { index, choice ->
                ChoiceButton(
                    text = choice,
                    enabled = !answered,
                    state = when {
                        !answered -> ChoiceState.Default
                        index == question.answerIndex -> ChoiceState.Correct
                        index == selectedIndex -> ChoiceState.Wrong
                        else -> ChoiceState.Revealed
                    },
                    onClick = { onPick(index) },
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BgCard)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (answered) {
                PrimaryButton(
                    text = "Next",
                    onClick = onNext,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            TextButton(onClick = onBackToMenu) {
                Text(
                    text = "← Back to menu",
                    color = TextMuted,
                )
            }
        }
    }
}

private enum class ChoiceState {
    Default,
    Correct,
    Wrong,
    Revealed,
}

@Composable
private fun ChoiceButton(
    text: String,
    enabled: Boolean,
    state: ChoiceState,
    onClick: () -> Unit,
) {
    val background = when (state) {
        ChoiceState.Default -> BgElevated
        ChoiceState.Correct -> Color(0x1F3DD68C)
        ChoiceState.Wrong -> Color(0x1AFF5C5C)
        ChoiceState.Revealed -> BgElevated
    }
    val borderColor = when (state) {
        ChoiceState.Default -> Color.Transparent
        ChoiceState.Correct -> Correct
        ChoiceState.Wrong -> Wrong
        ChoiceState.Revealed -> Color.Transparent
    }
    val alpha = if (state == ChoiceState.Revealed) 0.55f else 1f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(background)
            .border(2.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha),
        )
    }
}
