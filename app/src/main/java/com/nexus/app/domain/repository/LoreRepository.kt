package com.nexus.app.domain.repository

import com.nexus.app.domain.model.LoreCard

interface LoreRepository {
    suspend fun getLoreCardsForCharacter(characterId: String): List<LoreCard>
    suspend fun getLoreCardsForIssue(issueId: String): List<LoreCard>
    suspend fun getLoreCardById(id: String): LoreCard?
}
