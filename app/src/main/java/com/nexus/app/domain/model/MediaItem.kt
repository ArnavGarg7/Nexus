package com.nexus.app.domain.model

data class MediaItem(
    val id: String,
    val title: String,
    val type: MediaType,
    val studio: String,
    val releaseYear: Int,
    val synopsis: String,
    val posterUrl: String,
    val relatedComicIds: List<String>,
    val characterIds: List<String>,
    val tags: List<String>
)

enum class MediaType { FILM, TV_SERIES, GAME, ANIMATED, PODCAST }
