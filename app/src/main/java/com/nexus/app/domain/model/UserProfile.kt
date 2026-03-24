package com.nexus.app.domain.model

data class UserProfile(
    val id: String,
    val displayName: String,
    val fanLevel: Int,
    val xpPoints: Int,
    val badges: List<String>,
    val streakDays: Int,
    val preferredPublishers: List<Publisher>,
    val preferredTags: List<String>,
    val readingListIds: List<String>,
    val completedQuizIds: List<String>
)
