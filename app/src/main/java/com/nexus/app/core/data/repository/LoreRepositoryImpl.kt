package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.core.data.remote.GeminiService
import com.nexus.app.domain.model.LoreCard
import com.nexus.app.domain.repository.LoreRepository
import javax.inject.Inject

class LoreRepositoryImpl @Inject constructor(
    private val mockData: MockDataProvider,
    private val geminiService: GeminiService
) : LoreRepository {

    override suspend fun getLoreCardsForCharacter(characterId: String): List<LoreCard> {
        val mock = mockData.getLoreForCharacter(characterId)
        if (mock.isNotEmpty()) return mock
        // Fall back to AI-generated lore
        return try {
            val name = mockData.getCharacterById(characterId)?.name ?: characterId
            geminiService.generateLore(name, characterId)
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getLoreCardsForIssue(issueId: String): List<LoreCard> =
        mockData.loreCards.filter { it.relatedEntityId == issueId }

    override suspend fun getLoreCardById(id: String): LoreCard? =
        mockData.loreCards.find { it.id == id }
}
