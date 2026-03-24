package com.nexus.app.features.events.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.domain.model.LoreCard
import com.nexus.app.domain.model.StoryArc
import com.nexus.app.domain.repository.LoreRepository
import com.nexus.app.domain.usecase.event.GetEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EventDetailUiState(
    val event: StoryArc? = null,
    val loreCards: List<LoreCard> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val getEvent: GetEventUseCase,
    private val loreRepository: LoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val eventId: String = savedStateHandle["eventId"] ?: ""
    private val _state = MutableStateFlow(EventDetailUiState())
    val state: StateFlow<EventDetailUiState> = _state

    init { loadEvent() }

    private fun loadEvent() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val event = getEvent(eventId)
                val lore = loreRepository.getLoreCardsForCharacter(eventId) // also checks events
                _state.value = EventDetailUiState(event = event, loreCards = lore, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}
