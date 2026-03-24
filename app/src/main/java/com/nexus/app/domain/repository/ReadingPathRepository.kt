package com.nexus.app.domain.repository

import com.nexus.app.domain.model.PathType
import com.nexus.app.domain.model.ReadingPath

interface ReadingPathRepository {
    suspend fun getReadingPathForCharacter(characterId: String, type: PathType): ReadingPath?
    suspend fun getReadingPathForEvent(eventId: String, type: PathType): ReadingPath?
    suspend fun getPathfinderPaths(tags: List<String>): List<ReadingPath>
    suspend fun saveUserReadingList(path: ReadingPath)
    suspend fun getUserReadingLists(userId: String): List<ReadingPath>
}
