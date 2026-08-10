package com.championwayappsprtchapwayway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.championwayappsprtchapwayway.data.QuizData
import com.championwayappsprtchapwayway.ui.components.AppBackground
import com.championwayappsprtchapwayway.ui.components.ScreenContainer
import com.championwayappsprtchapwayway.ui.screens.ConnectionErrorScreen
import com.championwayappsprtchapwayway.ui.screens.HomeScreen
import com.championwayappsprtchapwayway.ui.screens.PenaltyGameScreen
import com.championwayappsprtchapwayway.ui.screens.QuizScreen
import com.championwayappsprtchapwayway.ui.screens.ResultScreen
import com.championwayappsprtchapwayway.ui.screens.SplashScreen
import com.championwayappsprtchapwayway.ui.screens.StartupWaitingScreen
import com.championwayappsprtchapwayway.ui.theme.SportQuizzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportQuizzTheme {
                var startupState by remember { mutableStateOf(StartupState.Loading) }
                var screen by remember { mutableStateOf(AppScreen.Home) }
                var questionIndex by remember { mutableIntStateOf(0) }
                var score by remember { mutableIntStateOf(0) }
                var answered by remember { mutableStateOf(false) }
                var selectedIndex by remember { mutableStateOf<Int?>(null) }
                var gameResetKey by remember { mutableIntStateOf(0) }

                fun beginStartupPopup() {
                    OneSignalInApp.showStartupMessage(
                        hasInternet = { NetworkUtils.hasInternetConnection(this@MainActivity) },
                        onDismissed = { startupState = StartupState.Ready },
                        onContinueWithoutPopup = { startupState = StartupState.Ready },
                        onFailed = { startupState = StartupState.ConnectionError },
                    )
                }

                when (startupState) {
                    StartupState.Loading -> {
                        SplashScreen(
                            onFinished = {
                                startupState = StartupState.WaitingForPopup
                                beginStartupPopup()
                            },
                        )
                    }

                    StartupState.WaitingForPopup -> {
                        StartupWaitingScreen(modifier = Modifier.fillMaxSize())
                    }

                    StartupState.ConnectionError -> {
                        AppBackground {
                            ConnectionErrorScreen(
                                onRetry = {
                                    startupState = StartupState.WaitingForPopup
                                    beginStartupPopup()
                                },
                            )
                        }
                    }

                    StartupState.Ready -> {
                        AppBackground {
                            when (screen) {
                                AppScreen.Game -> PenaltyGameScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    resetKey = gameResetKey,
                                    onBackToMenu = { screen = AppScreen.Home },
                                )

                                AppScreen.Quiz -> {
                                    val question = QuizData.questions[questionIndex]
                                    QuizScreen(
                                        modifier = Modifier.fillMaxSize(),
                                        questionIndex = questionIndex,
                                        totalQuestions = QuizData.questions.size,
                                        score = score,
                                        question = question,
                                        answered = answered,
                                        selectedIndex = selectedIndex,
                                        onPick = { index ->
                                            if (answered) return@QuizScreen
                                            answered = true
                                            selectedIndex = index
                                            if (index == question.answerIndex) {
                                                score += 1
                                            }
                                        },
                                        onNext = {
                                            if (questionIndex + 1 >= QuizData.questions.size) {
                                                screen = AppScreen.Result
                                            } else {
                                                questionIndex += 1
                                                answered = false
                                                selectedIndex = null
                                            }
                                        },
                                        onBackToMenu = { screen = AppScreen.Home },
                                    )
                                }

                                else -> ScreenContainer {
                                    when (screen) {
                                        AppScreen.Home -> HomeScreen(
                                            onStartQuiz = {
                                                questionIndex = 0
                                                score = 0
                                                answered = false
                                                selectedIndex = null
                                                screen = AppScreen.Quiz
                                            },
                                            onOpenGame = {
                                                gameResetKey += 1
                                                screen = AppScreen.Game
                                            },
                                        )

                                        AppScreen.Result -> ResultScreen(
                                            score = score,
                                            message = QuizData.resultMessage(
                                                score,
                                                QuizData.questions.size,
                                            ),
                                            onPlayAgain = { screen = AppScreen.Home },
                                        )

                                        else -> Unit
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private enum class StartupState {
    Loading,
    WaitingForPopup,
    ConnectionError,
    Ready,
}

private enum class AppScreen {
    Home,
    Quiz,
    Result,
    Game,
}
