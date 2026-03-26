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
 * Client for the SuperHero API — cross-publisher character data.
 * Docs: https://superheroapi.com
 */
@Singleton
class SuperHeroApiService @Inject constructor() {

    private val client = HttpClient(OkHttp)
    private val gson = Gson()
    private val token = BuildConfig.SUPERHERO_API_TOKEN
    private val baseUrl = "https://superheroapi.com/api/$token"

    // ── Search ──────────────────────────────────────────────────────────

    suspend fun searchCharacter(name: String): List<SuperHeroResult> {
        return try {
            val response = client.get("$baseUrl/search/$name")
            if (!response.status.isSuccess()) return emptyList()
            val body = response.bodyAsText()
            val parsed = gson.fromJson(body, SuperHeroSearchResponse::class.java)
            if (parsed.response == "success") parsed.results ?: emptyList() else emptyList()
        } catch (e: Exception) {
            Timber.e(e, "SuperHero API search failed for: $name")
            emptyList()
        }
    }

    // ── Get by ID ───────────────────────────────────────────────────────

    suspend fun getCharacterById(id: String): SuperHeroResult? {
        return try {
            val response = client.get("$baseUrl/$id")
            if (!response.status.isSuccess()) return null
            val body = response.bodyAsText()
            gson.fromJson(body, SuperHeroResult::class.java)
        } catch (e: Exception) {
            Timber.e(e, "SuperHero API get failed for id: $id")
            null
        }
    }

    // ── Get Image Only ──────────────────────────────────────────────────

    suspend fun getCharacterImage(id: String): String? {
        return try {
            val response = client.get("$baseUrl/$id/image")
            if (!response.status.isSuccess()) return null
            val body = response.bodyAsText()
            val parsed = gson.fromJson(body, SuperHeroImageResponse::class.java)
            parsed.url
        } catch (e: Exception) {
            Timber.e(e, "SuperHero API image failed for id: $id")
            null
        }
    }

    // ── Get Powerstats ──────────────────────────────────────────────────

    suspend fun getCharacterPowerstats(id: String): SuperHeroPowerstats? {
        return try {
            val response = client.get("$baseUrl/$id/powerstats")
            if (!response.status.isSuccess()) return null
            val body = response.bodyAsText()
            gson.fromJson(body, SuperHeroPowerstats::class.java)
        } catch (e: Exception) {
            Timber.e(e, "SuperHero API powerstats failed for id: $id")
            null
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════
// DTOs
// ═══════════════════════════════════════════════════════════════════════

data class SuperHeroSearchResponse(
    val response: String,
    @SerializedName("results-for") val resultsFor: String?,
    val results: List<SuperHeroResult>?
)

data class SuperHeroResult(
    val id: String,
    val name: String,
    val powerstats: SuperHeroPowerstats?,
    val biography: SuperHeroBiography?,
    val appearance: SuperHeroAppearance?,
    val work: SuperHeroWork?,
    val connections: SuperHeroConnections?,
    val image: SuperHeroImage?
)

data class SuperHeroPowerstats(
    val intelligence: String?,
    val strength: String?,
    val speed: String?,
    val durability: String?,
    val power: String?,
    val combat: String?
)

data class SuperHeroBiography(
    @SerializedName("full-name") val fullName: String?,
    @SerializedName("alter-egos") val alterEgos: String?,
    val aliases: List<String>?,
    @SerializedName("place-of-birth") val placeOfBirth: String?,
    @SerializedName("first-appearance") val firstAppearance: String?,
    val publisher: String?,
    val alignment: String?
)

data class SuperHeroAppearance(
    val gender: String?,
    val race: String?,
    val height: List<String>?,
    val weight: List<String>?,
    @SerializedName("eye-color") val eyeColor: String?,
    @SerializedName("hair-color") val hairColor: String?
)

data class SuperHeroWork(
    val occupation: String?,
    val base: String?
)

data class SuperHeroConnections(
    @SerializedName("group-affiliation") val groupAffiliation: String?,
    val relatives: String?
)

data class SuperHeroImage(
    val url: String?
)

data class SuperHeroImageResponse(
    val response: String?,
    val id: String?,
    val name: String?,
    val url: String?
)
