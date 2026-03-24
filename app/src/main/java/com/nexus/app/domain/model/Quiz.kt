package com.nexus.app.domain.model

data class Quiz(
    val id: String,
    val title: String,
    val category: QuizCategory,
    val difficulty: QuizDifficulty,
    val questions: List<QuizQuestion>,
    val relatedEntityId: String?,
    val estimatedMinutes: Int,
    val xpReward: Int
)

data class QuizQuestion(
    val id: String,
    val type: QuestionType,
    val question: String,
    val imageUrl: String?,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val relatedLoreCardId: String?
)

enum class QuizCategory {
    CHARACTER, STORYLINE, UNIVERSE, MOVIE_TV, CREATOR, MIXED_LORE, EVENT, VILLAIN
}

enum class QuizDifficulty { CASUAL_FAN, HARDCORE_FAN, LORE_MASTER }

enum class QuestionType {
    MULTIPLE_CHOICE, IMAGE_BASED, TIMELINE_ORDER, QUOTE_IDENTIFICATION, EVENT_CONNECTION
}
