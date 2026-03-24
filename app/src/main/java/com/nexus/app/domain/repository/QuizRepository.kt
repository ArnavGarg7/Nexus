package com.nexus.app.domain.repository

import com.nexus.app.domain.model.Quiz
import com.nexus.app.domain.model.QuizCategory
import com.nexus.app.domain.model.QuizDifficulty

interface QuizRepository {
    suspend fun getQuizById(id: String): Quiz?
    suspend fun getQuizzesByCategory(category: QuizCategory): List<Quiz>
    suspend fun getQuizzesForCharacter(characterId: String): List<Quiz>
    suspend fun generateDynamicQuiz(entityId: String, difficulty: QuizDifficulty): Quiz
    suspend fun saveQuizResult(userId: String, quizId: String, score: Int, xpEarned: Int)
    suspend fun getRecommendedQuizzes(userId: String): List<Quiz>
}
