package com.rundsa.app.models

data class ProgressModel(
    val topic: String,
    val score: String,
    val runs: Long,
    val progress: Int
)