package com.nexus.app.core.data.local.dao

import androidx.room.*
import com.nexus.app.core.data.local.entities.QuizEntity
import com.nexus.app.core.data.local.entities.QuizResultEntity

@Dao
interface QuizDao {
    @Query("SELECT * FROM quizzes WHERE id = :id")
    suspend fun getQuizById(id: String): QuizEntity?

    @Query("SELECT * FROM quizzes WHERE category = :category")
    suspend fun getQuizzesByCategory(category: String): List<QuizEntity>

    @Query("SELECT * FROM quizzes WHERE relatedEntityId = :entityId")
    suspend fun getQuizzesForEntity(entityId: String): List<QuizEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: QuizEntity)

    @Insert
    suspend fun insertQuizResult(result: QuizResultEntity)

    @Query("SELECT * FROM quiz_results WHERE userId = :userId ORDER BY completedAt DESC")
    suspend fun getResultsForUser(userId: String): List<QuizResultEntity>
}
