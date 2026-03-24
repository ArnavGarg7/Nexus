package com.nexus.app.features.mediabridge.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.domain.model.MediaItem
import com.nexus.app.domain.model.StoryArc
import com.nexus.app.domain.repository.EventRepository
import com.nexus.app.domain.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MediaBridgeUiState(
    val media: MediaItem? = null,
    val relatedArcs: List<StoryArc> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class MediaBridgeViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val eventRepository: EventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val mediaId: String = savedStateHandle["mediaId"] ?: ""
    private val _state = MutableStateFlow(MediaBridgeUiState())
    val state: StateFlow<MediaBridgeUiState> = _state

    init { loadMedia() }

    private fun loadMedia() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val media = mediaRepository.getMediaById(mediaId)
                // Fetch related arcs/events by IDs
                val arcs = media?.relatedComicIds?.mapNotNull { eventRepository.getEventById(it) } ?: emptyList()
                _state.value = MediaBridgeUiState(media = media, relatedArcs = arcs, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}
