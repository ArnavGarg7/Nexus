package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.core.data.remote.GeminiService
import com.nexus.app.domain.model.Quiz
import com.nexus.app.domain.model.QuizCategory
import com.nexus.app.domain.model.QuizDifficulty
import com.nexus.app.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val mockData: MockDataProvider,
    private val geminiService: GeminiService
) : QuizRepository {

    override suspend fun getQuizById(id: String): Quiz? =
        mockData.quizzes.find { it.id == id }

    override suspend fun getQuizzesByCategory(category: QuizCategory): List<Quiz> =
        mockData.quizzes.filter { it.category == category }

    override suspend fun getQuizzesForCharacter(characterId: String): List<Quiz> =
        mockData.getQuizzesForCharacter(characterId)

    override suspend fun generateDynamicQuiz(entityId: String, difficulty: QuizDifficulty): Quiz {
        // Try AI generation first, then fall back to mock
        return try {
            geminiService.generateQuiz(entityId, difficulty.name)
                ?: mockData.quizzes.firstOrNull { it.relatedEntityId == entityId }
                ?: mockData.quizzes.first()
        } catch (_: Exception) {
            mockData.quizzes.firstOrNull { it.relatedEntityId == entityId }
                ?: mockData.quizzes.first()
        }
    }

    override suspend fun saveQuizResult(userId: String, quizId: String, score: Int, xpEarned: Int) {
        // TODO: Save to Room for offline persistence
    }

    override suspend fun getRecommendedQuizzes(userId: String): List<Quiz> {
        return try {
            val aiQuizzes = geminiService.generateRecommendedQuizzes()
            aiQuizzes.ifEmpty { mockData.getRecommendedQuizzes() }
        } catch (_: Exception) {
            mockData.getRecommendedQuizzes()
        }
    }
}
