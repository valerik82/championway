package com.championwayappsprtchapwayway.data

data class Question(
    val text: String,
    val choices: List<String>,
    val answerIndex: Int,
)

object QuizData {
    val questions: List<Question> = listOf(
        Question(
            text = "How long is a standard NBA quarter (regulation time)?",
            choices = listOf("10 minutes", "12 minutes", "15 minutes", "20 minutes"),
            answerIndex = 1,
        ),
        Question(
            text = "In association football (soccer), what is the maximum number of outfield players one team can have on the pitch?",
            choices = listOf("9", "10", "11", "12"),
            answerIndex = 1,
        ),
        Question(
            text = "The Olympic rings represent five continents. Which color is NOT one of the ring colors?",
            choices = listOf("Black", "Green", "Orange", "Yellow"),
            answerIndex = 2,
        ),
        Question(
            text = "In tennis, what is the term for a score of zero?",
            choices = listOf("Nil", "Love", "Blank", "Zero"),
            answerIndex = 1,
        ),
        Question(
            text = "How many players are on the field for one American football team during a play?",
            choices = listOf("9", "10", "11", "12"),
            answerIndex = 2,
        ),
        Question(
            text = "Which country has won the most FIFA Men's World Cup titles as of common records?",
            choices = listOf("Germany", "Argentina", "Italy", "Brazil"),
            answerIndex = 3,
        ),
        Question(
            text = "What distance is a marathon race (approximately)?",
            choices = listOf("40.2 km", "42.195 km", "45 km", "50 km"),
            answerIndex = 1,
        ),
        Question(
            text = "In baseball, how many strikes result in a strikeout?",
            choices = listOf("2", "3", "4", "5"),
            answerIndex = 1,
        ),
        Question(
            text = "The Stanley Cup is awarded in which sport?",
            choices = listOf("Basketball", "Ice hockey", "Lacrosse", "Field hockey"),
            answerIndex = 1,
        ),
        Question(
            text = "In volleyball, how many hits is a team normally allowed before sending the ball over the net?",
            choices = listOf("2", "3", "4", "Unlimited"),
            answerIndex = 1,
        ),
        Question(
            text = "Which sport uses the terms 'birdie' and 'eagle'?",
            choices = listOf("Tennis", "Cricket", "Golf", "Badminton"),
            answerIndex = 2,
        ),
        Question(
            text = "How many periods are played in a standard NHL regulation game?",
            choices = listOf("2", "3", "4", "5"),
            answerIndex = 1,
        ),
        Question(
            text = "In cricket, what is a score of six runs from one ball usually called?",
            choices = listOf("A boundary", "A six", "A maximum over", "A ton"),
            answerIndex = 1,
        ),
        Question(
            text = "The Tour de France is primarily which type of event?",
            choices = listOf("Marathon running", "Road cycling", "Triathlon", "Motor racing"),
            answerIndex = 1,
        ),
        Question(
            text = "In rugby union, what is the value of a successful try (before any conversion)?",
            choices = listOf("3 points", "4 points", "5 points", "7 points"),
            answerIndex = 2,
        ),
    )

    fun resultMessage(score: Int, total: Int): String {
        val base = when {
            score == total -> "Perfect! You're a sports encyclopedia."
            score >= 12 -> "Excellent — serious fan territory."
            score >= 8 -> "Solid game — keep watching and learning."
            else -> "Room to grow — that's what trivia is for."
        }
        return "$base ($score / $total)"
    }
}
