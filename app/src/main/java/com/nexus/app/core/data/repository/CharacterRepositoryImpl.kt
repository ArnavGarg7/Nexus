package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.core.data.local.dao.CharacterDao
import com.nexus.app.core.data.remote.api.NexusApiService
import com.nexus.app.core.data.remote.dto.toDomain
import com.nexus.app.domain.model.Character
import com.nexus.app.domain.model.Publisher
import com.nexus.app.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: NexusApiService,
    private val dao: CharacterDao,
    private val mockData: MockDataProvider
) : CharacterRepository {

    override fun searchCharacters(query: String): Flow<List<Character>> = flow {
        // Try local DB first
        val local = try {
            dao.searchCharacters(query).let { dbFlow ->
                var result: List<Character> = emptyList()
                dbFlow.collect { entities ->
                    result = entities.map { it.toDomain() }
                    return@collect
                }
                result
            }
        } catch (_: Exception) { emptyList() }

        if (local.isNotEmpty()) {
            emit(local)
        } else {
            // Fall back to mock data
            val filtered = mockData.characters.filter { char ->
                char.name.contains(query, ignoreCase = true) ||
                char.aliases.any { it.contains(query, ignoreCase = true) }
            }
            emit(filtered)
        }
    }

    override suspend fun getCharacterById(id: String): Character? {
        val local = try { dao.getCharacterById(id)?.toDomain() } catch (_: Exception) { null }
        if (local != null) return local

        val remote = try { api.getCharacterById(id).body()?.toDomain() } catch (_: Exception) { null }
        if (remote != null) return remote

        return mockData.getCharacterById(id)
    }

    override suspend fun getCharactersByPublisher(publisher: Publisher): List<Character> {
        val local = try { dao.getCharactersByPublisher(publisher.name).map { it.toDomain() } } catch (_: Exception) { emptyList() }
        if (local.isNotEmpty()) return local
        return mockData.characters.filter { it.publisher == publisher }
    }

    override suspend fun getSimilarCharacters(characterId: String): List<Character> {
        return try {
            api.getSimilarCharacters(characterId).body()?.map { it.toDomain() } ?: mockData.getSimilarCharacters(characterId)
        } catch (_: Exception) {
            mockData.getSimilarCharacters(characterId)
        }
    }

    override suspend fun getFeaturedCharacters(): List<Character> {
        return try {
            api.getFeaturedCharacters().body()?.map { it.toDomain() } ?: mockData.getFeaturedCharacters()
        } catch (_: Exception) {
            mockData.getFeaturedCharacters()
        }
    }
}
