package com.rundsa.app.models

data class QuizQuestionModel(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)