package com.nexus.app.domain.model

data class ComicIssue(
    val id: String,
    val title: String,
    val issueNumber: String,
    val series: String,
    val publisher: Publisher,
    val releaseYear: Int,
    val writer: String,
    val artist: String,
    val coverArtist: String,
    val synopsis: String,
    val coverImageUrl: String,
    val tags: List<String>,
    val storyArcId: String?,
    val importanceRating: Int, // 1-5
    val isKeyIssue: Boolean
)
