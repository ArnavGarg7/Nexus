package com.nexus.app.domain.repository

import com.nexus.app.domain.model.Character
import com.nexus.app.domain.model.Publisher
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun searchCharacters(query: String): Flow<List<Character>>
    suspend fun getCharacterById(id: String): Character?
    suspend fun getCharactersByPublisher(publisher: Publisher): List<Character>
    suspend fun getSimilarCharacters(characterId: String): List<Character>
    suspend fun getFeaturedCharacters(): List<Character>
}
