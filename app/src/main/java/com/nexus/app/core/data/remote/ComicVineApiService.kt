package com.nexus.app.core.data.remote

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.nexus.app.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Client for the Comic Vine API — comprehensive comic data (DC, Image, Indies, etc.).
 * Docs: https://comicvine.gamespot.com/api/documentation
 */
@Singleton
class ComicVineApiService @Inject constructor() {

    private val client = HttpClient(OkHttp)
    private val gson = Gson()
    private val apiKey = BuildConfig.COMIC_VINE_API_KEY
    private val baseUrl = "https://comicvine.gamespot.com/api"

    // ── Search Characters ───────────────────────────────────────────────

    suspend fun searchCharacters(query: String, limit: Int = 10): List<ComicVineCharacter> {
        return try {
            val response = client.get("$baseUrl/search/") {
                parameter("api_key", apiKey)
                parameter("format", "json")
                parameter("query", query)
                parameter("resources", "character")
                parameter("limit", limit)
                parameter("field_list", "id,name,real_name,deck,image,first_appeared_in_issue,publisher,aliases")
            }
            if (!response.status.isSuccess()) return emptyList()
            val body = response.bodyAsText()
            val parsed = gson.fromJson(body, ComicVineSearchResponse::class.java)
            parsed.results ?: emptyList()
        } catch (e: Exception) {
            Timber.e(e, "Comic Vine search failed for: $query")
            emptyList()
        }
    }

    // ── Get Character Detail ────────────────────────────────────────────

    suspend fun getCharacterDetail(characterId: Long): ComicVineCharacterDetail? {
        return try {
            val response = client.get("$baseUrl/character/4005-$characterId/") {
                parameter("api_key", apiKey)
                parameter("format", "json")
                parameter("field_list", "id,name,real_name,deck,description,image,first_appeared_in_issue,publisher,aliases,origin,powers,teams,story_arc_credits")
            }
            if (!response.status.isSuccess()) return null
            val body = response.bodyAsText()
            val parsed = gson.fromJson(body, ComicVineDetailResponse::class.java)
            parsed.results
        } catch (e: Exception) {
            Timber.e(e, "Comic Vine character detail failed for id: $characterId")
            null
        }
    }

    // ── Search Story Arcs / Events ──────────────────────────────────────

    suspend fun searchStoryArcs(query: String, limit: Int = 10): List<ComicVineStoryArc> {
        return try {
            val response = client.get("$baseUrl/search/") {
                parameter("api_key", apiKey)
                parameter("format", "json")
                parameter("query", query)
                parameter("resources", "story_arc")
                parameter("limit", limit)
                parameter("field_list", "id,name,deck,image,publisher,first_appeared_in_issue,count_of_isssue_appearances")
            }
            if (!response.status.isSuccess()) return emptyList()
            val body = response.bodyAsText()
            val parsed = gson.fromJson(body, ComicVineStoryArcSearchResponse::class.java)
            parsed.results ?: emptyList()
        } catch (e: Exception) {
            Timber.e(e, "Comic Vine story arc search failed for: $query")
            emptyList()
        }
    }

    // ── Search Issues ───────────────────────────────────────────────────

    suspend fun searchIssues(query: String, limit: Int = 10): List<ComicVineIssue> {
        return try {
            val response = client.get("$baseUrl/search/") {
                parameter("api_key", apiKey)
                parameter("format", "json")
                parameter("query", query)
                parameter("resources", "issue")
                parameter("limit", limit)
                parameter("field_list", "id,name,issue_number,volume,deck,image,cover_date,store_date")
            }
            if (!response.status.isSuccess()) return emptyList()
            val body = response.bodyAsText()
            val parsed = gson.fromJson(body, ComicVineIssueSearchResponse::class.java)
            parsed.results ?: emptyList()
        } catch (e: Exception) {
            Timber.e(e, "Comic Vine issue search failed for: $query")
            emptyList()
        }
    }

    // ── Search Volumes (Series) ─────────────────────────────────────────

    suspend fun searchVolumes(query: String, limit: Int = 10): List<ComicVineVolume> {
        return try {
            val response = client.get("$baseUrl/search/") {
                parameter("api_key", apiKey)
                parameter("format", "json")
                parameter("query", query)
                parameter("resources", "volume")
                parameter("limit", limit)
                parameter("field_list", "id,name,deck,image,publisher,start_year,count_of_issues")
            }
            if (!response.status.isSuccess()) return emptyList()
            val body = response.bodyAsText()
            val parsed = gson.fromJson(body, ComicVineVolumeSearchResponse::class.java)
            parsed.results ?: emptyList()
        } catch (e: Exception) {
            Timber.e(e, "Comic Vine volume search failed for: $query")
            emptyList()
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════
// DTOs
// ═══════════════════════════════════════════════════════════════════════

// ── Search Responses ────────────────────────────────────────────────
data class ComicVineSearchResponse(
    @SerializedName("status_code") val statusCode: Int?,
    val error: String?,
    @SerializedName("number_of_total_results") val totalResults: Int?,
    val results: List<ComicVineCharacter>?
)

data class ComicVineDetailResponse(
    @SerializedName("status_code") val statusCode: Int?,
    val error: String?,
    val results: ComicVineCharacterDetail?
)

data class ComicVineStoryArcSearchResponse(
    @SerializedName("status_code") val statusCode: Int?,
    val results: List<ComicVineStoryArc>?
)

data class ComicVineIssueSearchResponse(
    @SerializedName("status_code") val statusCode: Int?,
    val results: List<ComicVineIssue>?
)

data class ComicVineVolumeSearchResponse(
    @SerializedName("status_code") val statusCode: Int?,
    val results: List<ComicVineVolume>?
)

// ── Entity DTOs ─────────────────────────────────────────────────────
data class ComicVineCharacter(
    val id: Long,
    val name: String?,
    @SerializedName("real_name") val realName: String?,
    val deck: String?,
    val image: ComicVineImage?,
    @SerializedName("first_appeared_in_issue") val firstAppearedInIssue: ComicVineFirstAppearance?,
    val publisher: ComicVinePublisher?,
    val aliases: String?
)

data class ComicVineCharacterDetail(
    val id: Long,
    val name: String?,
    @SerializedName("real_name") val realName: String?,
    val deck: String?,
    val description: String?,
    val image: ComicVineImage?,
    @SerializedName("first_appeared_in_issue") val firstAppearedInIssue: ComicVineFirstAppearance?,
    val publisher: ComicVinePublisher?,
    val aliases: String?,
    val origin: ComicVineOrigin?,
    val powers: List<ComicVinePower>?,
    val teams: List<ComicVineTeam>?,
    @SerializedName("story_arc_credits") val storyArcCredits: List<ComicVineStoryArcRef>?
)

data class ComicVineStoryArc(
    val id: Long,
    val name: String?,
    val deck: String?,
    val image: ComicVineImage?,
    val publisher: ComicVinePublisher?,
    @SerializedName("count_of_isssue_appearances") val issueCount: Int?
)

data class ComicVineIssue(
    val id: Long,
    val name: String?,
    @SerializedName("issue_number") val issueNumber: String?,
    val volume: ComicVineVolumeRef?,
    val deck: String?,
    val image: ComicVineImage?,
    @SerializedName("cover_date") val coverDate: String?,
    @SerializedName("store_date") val storeDate: String?
)

data class ComicVineVolume(
    val id: Long,
    val name: String?,
    val deck: String?,
    val image: ComicVineImage?,
    val publisher: ComicVinePublisher?,
    @SerializedName("start_year") val startYear: String?,
    @SerializedName("count_of_issues") val issueCount: Int?
)

// ── Nested DTOs ─────────────────────────────────────────────────────
data class ComicVineImage(
    @SerializedName("icon_url") val iconUrl: String?,
    @SerializedName("medium_url") val mediumUrl: String?,
    @SerializedName("screen_url") val screenUrl: String?,
    @SerializedName("screen_large_url") val screenLargeUrl: String?,
    @SerializedName("small_url") val smallUrl: String?,
    @SerializedName("super_url") val superUrl: String?,
    @SerializedName("thumb_url") val thumbUrl: String?,
    @SerializedName("tiny_url") val tinyUrl: String?,
    @SerializedName("original_url") val originalUrl: String?
)

data class ComicVinePublisher(
    val id: Long?,
    val name: String?,
    @SerializedName("api_detail_url") val apiDetailUrl: String?
)

data class ComicVineFirstAppearance(
    val id: Long?,
    val name: String?,
    @SerializedName("issue_number") val issueNumber: String?,
    @SerializedName("api_detail_url") val apiDetailUrl: String?
)

data class ComicVineOrigin(
    val id: Long?,
    val name: String?
)

data class ComicVinePower(
    val id: Long?,
    val name: String?
)

data class ComicVineTeam(
    val id: Long?,
    val name: String?
)

data class ComicVineStoryArcRef(
    val id: Long?,
    val name: String?,
    @SerializedName("api_detail_url") val apiDetailUrl: String?
)

data class ComicVineVolumeRef(
    val id: Long?,
    val name: String?,
    @SerializedName("api_detail_url") val apiDetailUrl: String?
)
