package com.nexus.app.core.data.repository

import com.nexus.app.core.data.local.MockDataProvider
import com.nexus.app.core.data.local.dao.CharacterDao
import com.nexus.app.core.data.remote.ComicVineApiService
import com.nexus.app.core.data.remote.SuperHeroApiService
import com.nexus.app.core.data.remote.SuperHeroResult
import com.nexus.app.core.data.remote.ComicVineCharacter
import com.nexus.app.core.data.remote.dto.toDomain
import com.nexus.app.domain.model.Character
import com.nexus.app.domain.model.Publisher
import com.nexus.app.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val dao: CharacterDao,
    private val mockData: MockDataProvider,
    private val superHeroApi: SuperHeroApiService,
    private val comicVineApi: ComicVineApiService
) : CharacterRepository {

    override fun searchCharacters(query: String): Flow<List<Character>> = flow {
        // 1. Try local DB
        val local = try {
            var result: List<Character> = emptyList()
            dao.searchCharacters(query).collect { entities ->
                result = entities.map { it.toDomain() }
                return@collect
            }
            result
        } catch (_: Exception) { emptyList() }

        if (local.isNotEmpty()) {
            emit(local)
            return@flow
        }

        // 2. Try mock data (instant)
        val mockResults = mockData.characters.filter { char ->
            char.name.contains(query, ignoreCase = true) ||
            char.aliases.any { it.contains(query, ignoreCase = true) }
        }

        // 3. Try SuperHero API for broader results
        val apiResults = try {
            val results = superHeroApi.searchCharacter(query)
            results.mapNotNull { it.toDomainCharacter() }
        } catch (e: Exception) {
            Timber.e(e, "SuperHero API search failed, using mock data")
            emptyList()
        }

        // Merge: mock data first (higher quality), then API results (deduped)
        val mockIds = mockResults.map { it.name.lowercase() }.toSet()
        val merged = mockResults + apiResults.filter { it.name.lowercase() !in mockIds }
        emit(merged.ifEmpty { mockResults })
    }

    override suspend fun getCharacterById(id: String): Character? {
        // Try local DB
        val local = try { dao.getCharacterById(id)?.toDomain() } catch (_: Exception) { null }
        if (local != null) return local

        // Try mock data
        val mock = mockData.getCharacterById(id)
        if (mock != null) return mock

        // Try SuperHero API by ID (if numeric)
        return try {
            val numericId = id.removePrefix("sh_")
            val result = superHeroApi.getCharacterById(numericId)
            result?.toDomainCharacter()
        } catch (_: Exception) { null }
    }

    override suspend fun getCharactersByPublisher(publisher: Publisher): List<Character> {
        val local = try {
            dao.getCharactersByPublisher(publisher.name).map { it.toDomain() }
        } catch (_: Exception) { emptyList() }
        if (local.isNotEmpty()) return local
        return mockData.characters.filter { it.publisher == publisher }
    }

    override suspend fun getSimilarCharacters(characterId: String): List<Character> {
        return mockData.getSimilarCharacters(characterId)
    }

    override suspend fun getFeaturedCharacters(): List<Character> {
        return mockData.getFeaturedCharacters()
    }
}

// ═══════════════════════════════════════════════════════════════════════
// Mapping Extensions
// ═══════════════════════════════════════════════════════════════════════

/** Map SuperHero API result → domain Character */
fun SuperHeroResult.toDomainCharacter(): Character? {
    val bio = biography ?: return null
    return Character(
        id = "sh_$id",
        name = name,
        aliases = bio.aliases ?: emptyList(),
        publisher = when {
            bio.publisher?.contains("DC", ignoreCase = true) == true -> Publisher.DC
            bio.publisher?.contains("Marvel", ignoreCase = true) == true -> Publisher.MARVEL
            bio.publisher?.contains("Image", ignoreCase = true) == true -> Publisher.IMAGE
            bio.publisher?.contains("Dark Horse", ignoreCase = true) == true -> Publisher.DARK_HORSE
            else -> Publisher.INDIE
        },
        firstAppearance = bio.firstAppearance ?: "",
        creators = emptyList(),
        synopsis = "${bio.fullName ?: name}. ${bio.alignment?.replaceFirstChar { it.uppercase() } ?: ""} character from ${bio.publisher ?: "unknown publisher"}.",
        imageUrl = image?.url ?: "",
        coverUrl = image?.url ?: "",
        tags = buildList {
            if (bio.alignment == "good") add("Heroic")
            if (bio.alignment == "bad") add("Villain")
            if (bio.alignment == "neutral") add("Anti-Hero")
            powerstats?.let { ps ->
                if ((ps.intelligence?.toIntOrNull() ?: 0) > 80) add("Genius")
                if ((ps.strength?.toIntOrNull() ?: 0) > 80) add("Powerhouse")
                if ((ps.combat?.toIntOrNull() ?: 0) > 80) add("Fighter")
                if ((ps.speed?.toIntOrNull() ?: 0) > 80) add("Speedster")
            }
        },
        keyStoryArcs = emptyList(),
        relatedCharacterIds = emptyList(),
        mediaAppearances = emptyList()
    )
}

/** Map Comic Vine character → domain Character */
fun ComicVineCharacter.toDomainCharacter(): Character {
    return Character(
        id = "cv_$id",
        name = name ?: "Unknown",
        aliases = aliases?.split("\n")?.map { it.trim() }?.filter { it.isNotBlank() } ?: emptyList(),
        publisher = when {
            publisher?.name?.contains("DC", ignoreCase = true) == true -> Publisher.DC
            publisher?.name?.contains("Marvel", ignoreCase = true) == true -> Publisher.MARVEL
            publisher?.name?.contains("Image", ignoreCase = true) == true -> Publisher.IMAGE
            publisher?.name?.contains("Dark Horse", ignoreCase = true) == true -> Publisher.DARK_HORSE
            else -> Publisher.INDIE
        },
        firstAppearance = firstAppearedInIssue?.let { "${it.name ?: ""} #${it.issueNumber ?: ""}" } ?: "",
        creators = emptyList(),
        synopsis = deck ?: "",
        imageUrl = image?.superUrl ?: image?.originalUrl ?: "",
        coverUrl = image?.screenLargeUrl ?: image?.superUrl ?: "",
        tags = emptyList(),
        keyStoryArcs = emptyList(),
        relatedCharacterIds = emptyList(),
        mediaAppearances = emptyList()
    )
}
