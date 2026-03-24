package com.nexus.app.features.quiz.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.domain.model.Quiz
import com.nexus.app.domain.model.QuizCategory
import com.nexus.app.domain.usecase.quiz.GetQuizUseCase
import com.nexus.app.domain.usecase.quiz.GetRecommendedQuizzesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuizHubUiState(
    val recommended: List<Quiz> = emptyList(),
    val byCategory: Map<QuizCategory, List<Quiz>> = emptyMap(),
    val isLoading: Boolean = false
)

data class QuizUiState(
    val quiz: Quiz? = null,
    val currentIndex: Int = 0,
    val selectedAnswer: Int? = null,
    val isAnswerRevealed: Boolean = false,
    val score: Int = 0,
    val isComplete: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class QuizHubViewModel @Inject constructor(
    private val getRecommended: GetRecommendedQuizzesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(QuizHubUiState())
    val state: StateFlow<QuizHubUiState> = _state

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val recommended = getRecommended("current_user")
            _state.value = QuizHubUiState(recommended = recommended, isLoading = false)
        }
    }
}

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuiz: GetQuizUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val quizId: String = checkNotNull(savedStateHandle["quizId"])
    private val _state = MutableStateFlow(QuizUiState())
    val state: StateFlow<QuizUiState> = _state

    init { loadQuiz() }

    private fun loadQuiz() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val quiz = getQuiz(quizId)
            _state.value = QuizUiState(quiz = quiz, isLoading = false)
        }
    }

    fun selectAnswer(index: Int) {
        if (_state.value.isAnswerRevealed) return
        _state.value = _state.value.copy(selectedAnswer = index)
    }

    fun revealAnswer() {
        val state = _state.value
        val quiz = state.quiz ?: return
        val correct = quiz.questions[state.currentIndex].correctAnswerIndex
        val newScore = if (state.selectedAnswer == correct) state.score + 1 else state.score
        _state.value = state.copy(isAnswerRevealed = true, score = newScore)
    }

    fun nextQuestion() {
        val state = _state.value
        val quiz = state.quiz ?: return
        val next = state.currentIndex + 1
        if (next >= quiz.questions.size) {
            _state.value = state.copy(isComplete = true)
        } else {
            _state.value = state.copy(currentIndex = next, selectedAnswer = null, isAnswerRevealed = false)
        }
    }
}
