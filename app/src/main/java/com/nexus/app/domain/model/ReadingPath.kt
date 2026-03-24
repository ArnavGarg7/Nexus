package com.nexus.app.domain.model

data class ReadingPath(
    val id: String,
    val title: String,
    val description: String,
    val pathType: PathType,
    val issues: List<ReadingPathItem>,
    val estimatedHours: Int,
    val tags: List<String>
)

data class ReadingPathItem(
    val order: Int,
    val comicIssueId: String,
    val reason: String,
    val isEssential: Boolean
)

enum class PathType { ESSENTIAL, FULL_EXPERIENCE, BEGINNER, COMPLETIONIST, EVENT_TIE_IN }
