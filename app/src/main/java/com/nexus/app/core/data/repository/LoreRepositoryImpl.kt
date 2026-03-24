package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.core.data.remote.GeminiService
import com.nexus.app.core.data.remote.api.NexusApiService
import com.nexus.app.core.data.remote.dto.toDomain
import com.nexus.app.domain.model.LoreCard
import com.nexus.app.domain.repository.LoreRepository
import javax.inject.Inject

class LoreRepositoryImpl @Inject constructor(
    private val api: NexusApiService,
    private val mockData: MockDataProvider,
    private val geminiService: GeminiService
) : LoreRepository {

    override suspend fun getLoreCardsForCharacter(characterId: String): List<LoreCard> {
        return try {
            api.getLoreForCharacter(characterId).body()?.map { it.toDomain() }
                ?: mockData.getLoreForCharacter(characterId).ifEmpty {
                    geminiService.generateLore(characterId, characterId)
                }
        } catch (_: Exception) {
            mockData.getLoreForCharacter(characterId).ifEmpty {
                geminiService.generateLore(characterId, characterId)
            }
        }
    }

    override suspend fun getLoreCardsForIssue(issueId: String): List<LoreCard> =
        mockData.loreCards.filter { it.relatedEntityId == issueId }

    override suspend fun getLoreCardById(id: String): LoreCard? =
        mockData.loreCards.find { it.id == id }
}
