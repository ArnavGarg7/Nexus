package com.nexus.app.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val difficulty: String,
    val questionsJson: String,  // Full JSON
    val relatedEntityId: String?,
    val estimatedMinutes: Int,
    val xpReward: Int,
    val cachedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true) val resultId: Long = 0,
    val userId: String,
    val quizId: String,
    val score: Int,
    val totalQuestions: Int,
    val xpEarned: Int,
    val completedAt: Long = System.currentTimeMillis()
)
