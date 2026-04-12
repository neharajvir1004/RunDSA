package com.rundsa.app.models

data class TopicModel(
    val title: String,
    val icon: Int,
    val desc: String
)

data class TopicDetailModel(
    val title: String,
    val definition: String,
    val example: String,
    val code: String,
    val subtypes: List<SubtypeModel> = emptyList()
)

data class SubtypeModel(
    val name: String,
    val definition: String,
    val code: String
)