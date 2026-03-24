package com.nexus.app.domain.usecase.character

import com.nexus.app.domain.model.Character
import com.nexus.app.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: String): Character? = repository.getCharacterById(id)
}
