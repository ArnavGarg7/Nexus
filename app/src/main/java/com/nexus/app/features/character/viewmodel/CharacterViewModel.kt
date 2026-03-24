package com.nexus.app.features.character.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.domain.model.Character
import com.nexus.app.domain.model.ReadingPath
import com.nexus.app.domain.model.PathType
import com.nexus.app.domain.usecase.character.GetCharacterUseCase
import com.nexus.app.domain.usecase.character.GetSimilarCharactersUseCase
import com.nexus.app.domain.repository.ReadingPathRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharacterUiState(
    val character: Character? = null,
    val beginnerPath: ReadingPath? = null,
    val similarCharacters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacter: GetCharacterUseCase,
    private val getSimilarCharacters: GetSimilarCharactersUseCase,
    private val readingPathRepository: ReadingPathRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val characterId: String = checkNotNull(savedStateHandle["characterId"])
    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState: StateFlow<CharacterUiState> = _uiState

    init { loadCharacter() }

    private fun loadCharacter() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val character = getCharacter(characterId)
                val similar = getSimilarCharacters(characterId)
                val path = readingPathRepository.getReadingPathForCharacter(characterId, PathType.BEGINNER)
                _uiState.value = CharacterUiState(
                    character = character,
                    beginnerPath = path,
                    similarCharacters = similar,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}
