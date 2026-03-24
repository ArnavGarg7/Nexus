package com.nexus.app.domain.model

data class LoreCard(
    val id: String,
    val title: String,
    val content: String,
    val category: LoreCategory,
    val relatedEntityId: String,
    val relatedEntityType: String,
    val imageUrl: String?
)

enum class LoreCategory {
    ORIGIN, EASTER_EGG, CREATOR_INSIGHT, RETCON, REAL_WORLD_INFLUENCE, TRIVIA
}
