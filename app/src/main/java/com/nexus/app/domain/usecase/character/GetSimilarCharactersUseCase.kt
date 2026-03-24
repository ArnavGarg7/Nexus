package com.nexus.app.domain.usecase.character

import com.nexus.app.domain.model.Character
import com.nexus.app.domain.repository.CharacterRepository
import javax.inject.Inject

class GetSimilarCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(characterId: String): List<Character> =
        repository.getSimilarCharacters(characterId)
}
