package com.nexus.app.features.events.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.domain.model.StoryArc
import com.nexus.app.domain.usecase.event.GetAllEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EventListUiState(
    val events: List<StoryArc> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getAllEvents: GetAllEventsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EventListUiState())
    val state: StateFlow<EventListUiState> = _state

    init { load() }

    private fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val events = getAllEvents()
                _state.value = EventListUiState(events = events, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}
