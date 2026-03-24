package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.core.data.remote.GeminiService
import com.nexus.app.core.data.remote.api.NexusApiService
import com.nexus.app.core.data.remote.dto.GenerateQuizRequest
import com.nexus.app.core.data.remote.dto.QuizResultRequest
import com.nexus.app.core.data.remote.dto.toDomain
import com.nexus.app.domain.model.Quiz
import com.nexus.app.domain.model.QuizCategory
import com.nexus.app.domain.model.QuizDifficulty
import com.nexus.app.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val api: NexusApiService,
    private val mockData: MockDataProvider,
    private val geminiService: GeminiService
) : QuizRepository {

    override suspend fun getQuizById(id: String): Quiz? {
        return try {
            api.getQuizById(id).body()?.toDomain()
        } catch (_: Exception) {
            mockData.quizzes.find { it.id == id }
        }
    }

    override suspend fun getQuizzesByCategory(category: QuizCategory): List<Quiz> {
        return try {
            api.getQuizzesByCategory(category.name).body()?.map { it.toDomain() }
                ?: mockData.quizzes.filter { it.category == category }
        } catch (_: Exception) {
            mockData.quizzes.filter { it.category == category }
        }
    }

    override suspend fun getQuizzesForCharacter(characterId: String): List<Quiz> {
        return try {
            api.getQuizzesByCategory(characterId).body()?.map { it.toDomain() }
                ?: mockData.getQuizzesForCharacter(characterId)
        } catch (_: Exception) {
            mockData.getQuizzesForCharacter(characterId)
        }
    }

    override suspend fun generateDynamicQuiz(entityId: String, difficulty: QuizDifficulty): Quiz {
        return try {
            api.generateDynamicQuiz(GenerateQuizRequest(entityId, difficulty.name)).body()!!.toDomain()
        } catch (_: Exception) {
            // Try AI generation first, then fall back to mock
            geminiService.generateQuiz(entityId, difficulty.name)
                ?: mockData.quizzes.firstOrNull { it.relatedEntityId == entityId }
                ?: mockData.quizzes.first()
        }
    }

    override suspend fun saveQuizResult(userId: String, quizId: String, score: Int, xpEarned: Int) {
        try {
            api.submitQuizResult(quizId, QuizResultRequest(userId, score, xpEarned))
        } catch (_: Exception) {
            // silent fallback — quiz results stored locally in future
        }
    }

    override suspend fun getRecommendedQuizzes(userId: String): List<Quiz> {
        return try {
            api.getRecommendedQuizzes(userId).body()?.map { it.toDomain() }
                ?: geminiService.generateRecommendedQuizzes().ifEmpty {
                    mockData.getRecommendedQuizzes()
                }
        } catch (_: Exception) {
            geminiService.generateRecommendedQuizzes().ifEmpty {
                mockData.getRecommendedQuizzes()
            }
        }
    }
}
