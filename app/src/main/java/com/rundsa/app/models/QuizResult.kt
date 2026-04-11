package com.rundsa.app.models

data class QuizResult(
    val userId: String = "",
    val email: String = "",
    val topic: String = "",
    val level: String = "",
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)