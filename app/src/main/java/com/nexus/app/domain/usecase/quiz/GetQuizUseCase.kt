package com.nexus.app.domain.usecase.quiz

import com.nexus.app.domain.model.Quiz
import com.nexus.app.domain.repository.QuizRepository
import javax.inject.Inject

class GetQuizUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(quizId: String): Quiz? = repository.getQuizById(quizId)
}
