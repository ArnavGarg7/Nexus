package com.nexus.app.features.reading.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.domain.model.PathType
import com.nexus.app.domain.model.ReadingPath
import com.nexus.app.domain.repository.ReadingPathRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReadingOrderUiState(
    val path: ReadingPath? = null,
    val isEssentialMode: Boolean = true,
    val isLoading: Boolean = false
)

@HiltViewModel
class ReadingOrderViewModel @Inject constructor(
    private val repository: ReadingPathRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val entityId: String = savedStateHandle["entityId"] ?: ""
    private val entityType: String = savedStateHandle["entityType"] ?: "character"
    private val _state = MutableStateFlow(ReadingOrderUiState())
    val state: StateFlow<ReadingOrderUiState> = _state

    init { loadPath(true) }

    fun toggleMode(essential: Boolean) {
        _state.value = _state.value.copy(isEssentialMode = essential)
        loadPath(essential)
    }

    private fun loadPath(essential: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val type = if (essential) PathType.ESSENTIAL else PathType.FULL_EXPERIENCE
            val path = if (entityType == "character")
                repository.getReadingPathForCharacter(entityId, type)
            else
                repository.getReadingPathForEvent(entityId, type)
            _state.value = ReadingOrderUiState(path = path, isEssentialMode = essential, isLoading = false)
        }
    }
}
