package com.nexus.app.domain.usecase.character

import com.nexus.app.domain.model.Character
import com.nexus.app.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(query: String): Flow<List<Character>> = repository.searchCharacters(query)
}
