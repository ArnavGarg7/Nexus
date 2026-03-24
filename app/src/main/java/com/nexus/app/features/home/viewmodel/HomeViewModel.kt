package com.nexus.app.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.domain.model.Character
import com.nexus.app.domain.model.MediaItem
import com.nexus.app.domain.model.Quiz
import com.nexus.app.domain.model.StoryArc
import com.nexus.app.domain.repository.CharacterRepository
import com.nexus.app.domain.repository.EventRepository
import com.nexus.app.domain.usecase.quiz.GetRecommendedQuizzesUseCase
import com.nexus.app.core.data.local.MockDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val featuredCharacters: List<Character> = emptyList(),
    val recommendedQuizzes: List<Quiz> = emptyList(),
    val featuredEvents: List<StoryArc> = emptyList(),
    val featuredMedia: List<MediaItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecommendedQuizzes: GetRecommendedQuizzesUseCase,
    private val characterRepository: CharacterRepository,
    private val eventRepository: EventRepository,
    private val mockData: MockDataProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init { loadHome() }

    private fun loadHome() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val characters = characterRepository.getFeaturedCharacters()
                val quizzes = getRecommendedQuizzes("current_user")
                val events = eventRepository.getAllEvents().filter { it.isEvent }.take(4)
                val media = mockData.mediaItems.take(4)
                _uiState.value = HomeUiState(
                    featuredCharacters = characters,
                    recommendedQuizzes = quizzes,
                    featuredEvents = events,
                    featuredMedia = media,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}
