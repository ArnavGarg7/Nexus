package com.nexus.app.domain.model

data class StoryArc(
    val id: String,
    val title: String,
    val publisher: Publisher,
    val issueIds: List<String>,
    val essentialIssueIds: List<String>,
    val writer: String,
    val startYear: Int,
    val endYear: Int?,
    val synopsis: String,
    val coverImageUrl: String,
    val tags: List<String>,
    val isEvent: Boolean,
    val preEventArcIds: List<String>,
    val postEventArcIds: List<String>
)
