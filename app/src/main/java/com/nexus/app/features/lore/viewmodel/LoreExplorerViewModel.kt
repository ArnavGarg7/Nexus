package com.nexus.app.features.lore.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.domain.model.LoreCard
import com.nexus.app.domain.repository.LoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoreUiState(val cards: List<LoreCard> = emptyList(), val isLoading: Boolean = false)

@HiltViewModel
class LoreExplorerViewModel @Inject constructor(
    private val loreRepository: LoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val characterId: String = savedStateHandle["characterId"] ?: ""
    private val _state = MutableStateFlow(LoreUiState())
    val state: StateFlow<LoreUiState> = _state

    init {
        viewModelScope.launch {
            _state.value = LoreUiState(isLoading = true)
            val cards = loreRepository.getLoreCardsForCharacter(characterId)
            _state.value = LoreUiState(cards = cards, isLoading = false)
        }
    }
}
