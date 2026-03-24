package com.nexus.app.domain.model

data class Character(
    val id: String,
    val name: String,
    val aliases: List<String>,
    val publisher: Publisher,
    val firstAppearance: String,
    val creators: List<String>,
    val synopsis: String,
    val imageUrl: String,
    val coverUrl: String,
    val tags: List<String>,
    val keyStoryArcs: List<String>,
    val relatedCharacterIds: List<String>,
    val mediaAppearances: List<String>
)

enum class Publisher {
    MARVEL, DC, IMAGE, DARK_HORSE, IDW, BOOM, INDIE, WILDSTORM, VALIANT
}
