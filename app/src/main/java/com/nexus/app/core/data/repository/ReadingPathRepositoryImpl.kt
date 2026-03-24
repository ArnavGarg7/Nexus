package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.core.data.remote.GeminiService
import com.nexus.app.core.data.remote.api.NexusApiService
import com.nexus.app.core.data.remote.dto.toDomain
import com.nexus.app.domain.model.PathType
import com.nexus.app.domain.model.ReadingPath
import com.nexus.app.domain.repository.ReadingPathRepository
import javax.inject.Inject

class ReadingPathRepositoryImpl @Inject constructor(
    private val api: NexusApiService,
    private val mockData: MockDataProvider,
    private val geminiService: GeminiService
) : ReadingPathRepository {

    override suspend fun getReadingPathForCharacter(characterId: String, type: PathType): ReadingPath? {
        return try {
            api.getReadingPathForCharacter(characterId, type.name).body()?.toDomain()
        } catch (_: Exception) {
            mockData.getReadingPathForCharacter(characterId)
        }
    }

    override suspend fun getReadingPathForEvent(eventId: String, type: PathType): ReadingPath? {
        return try {
            api.getReadingPathForEvent(eventId, type.name).body()?.toDomain()
        } catch (_: Exception) {
            // Return a path built from the event's issue list
            val event = mockData.getEventById(eventId) ?: return null
            val issues = if (type == PathType.ESSENTIAL) event.essentialIssueIds else event.issueIds
            ReadingPath(
                id = "${eventId}_path",
                title = "${event.title} — ${if (type == PathType.ESSENTIAL) "Essential" else "Full Experience"}",
                description = event.synopsis,
                pathType = type,
                issues = issues.mapIndexed { i, id ->
                    com.nexus.app.domain.model.ReadingPathItem(i + 1, id, "Core event issue", id in event.essentialIssueIds)
                },
                estimatedHours = issues.size,
                tags = event.tags
            )
        }
    }

    override suspend fun getPathfinderPaths(tags: List<String>): List<ReadingPath> {
        return try {
            api.getPathfinderPaths(tags).body()?.map { it.toDomain() } ?: getPathfinderWithAi(tags)
        } catch (_: Exception) {
            getPathfinderWithAi(tags)
        }
    }

    private suspend fun getPathfinderWithAi(tags: List<String>): List<ReadingPath> {
        // Try AI generation first, then mock
        val aiPaths = geminiService.generateReadingPath(tags)
        if (aiPaths.isNotEmpty()) return aiPaths
        return getPathfinderMock(tags)
    }

    private fun getPathfinderMock(tags: List<String>): List<ReadingPath> {
        if (tags.isEmpty()) return mockData.readingPaths
        return mockData.readingPaths.filter { path ->
            path.tags.any { it in tags }
        }.ifEmpty { mockData.readingPaths }
    }

    override suspend fun saveUserReadingList(path: ReadingPath) { /* TODO: save to Room */ }
    override suspend fun getUserReadingLists(userId: String): List<ReadingPath> = emptyList()
}
