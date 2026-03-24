package com.nexus.app.features.pathfinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.domain.model.ReadingPath
import com.nexus.app.domain.usecase.pathfinder.GetPathfinderResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PathfinderUiState(
    val selectedTags: List<String> = emptyList(),
    val results: List<ReadingPath> = emptyList(),
    val isLoading: Boolean = false,
    val hasResults: Boolean = false
)

@HiltViewModel
class PathfinderViewModel @Inject constructor(
    private val getPathfinderResults: GetPathfinderResultsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PathfinderUiState())
    val state: StateFlow<PathfinderUiState> = _state

    fun toggleTag(tag: String) {
        val current = _state.value.selectedTags.toMutableList()
        if (tag in current) current.remove(tag) else current.add(tag)
        _state.value = _state.value.copy(selectedTags = current, hasResults = false)
    }

    fun generatePaths() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val paths = getPathfinderResults(_state.value.selectedTags)
            _state.value = _state.value.copy(results = paths, isLoading = false, hasResults = true)
        }
    }
}
