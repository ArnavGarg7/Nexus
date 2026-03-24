package com.nexus.app.domain.usecase.quiz

import com.nexus.app.domain.model.Quiz
import com.nexus.app.domain.repository.QuizRepository
import javax.inject.Inject

class GetRecommendedQuizzesUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(userId: String): List<Quiz> =
        repository.getRecommendedQuizzes(userId)
}
