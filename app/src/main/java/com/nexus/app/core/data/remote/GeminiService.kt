package com.nexus.app.core.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nexus.app.BuildConfig
import com.nexus.app.domain.model.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * AI Agent service that uses Google Gemini to dynamically generate
 * quizzes, lore cards, and reading paths on-the-fly.
 */
@Singleton
class GeminiService @Inject constructor() {

    private val model = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = BuildConfig.GEMINI_API_KEY,
        generationConfig = generationConfig {
            temperature = 0.8f
            topK = 40
            topP = 0.95f
            maxOutputTokens = 4096
        }
    )

    private val gson = Gson()

    // ═══════════════════════════════════════════════════════════════
    // Quiz Generation
    // ═══════════════════════════════════════════════════════════════

    suspend fun generateQuiz(
        topic: String,
        difficulty: String = "CASUAL_FAN",
        questionCount: Int = 5
    ): Quiz? {
        val prompt = """
            You are a comic book expert AI. Generate a quiz about "$topic" for a comic book fan app.
            Difficulty level: $difficulty
            
            Return ONLY valid JSON (no markdown, no code blocks) in this exact format:
            {
              "id": "ai_quiz_${System.currentTimeMillis()}",
              "title": "Quiz title here",
              "category": "CHARACTER",
              "difficulty": "$difficulty",
              "questions": [
                {
                  "id": "q1",
                  "type": "MULTIPLE_CHOICE",
                  "question": "Question text here?",
                  "imageUrl": null,
                  "options": ["Option A", "Option B", "Option C", "Option D"],
                  "correctAnswerIndex": 0,
                  "explanation": "Explanation of the correct answer",
                  "relatedLoreCardId": null
                }
              ],
              "relatedEntityId": null,
              "estimatedMinutes": 5,
              "xpReward": 50
            }
            
            Generate exactly $questionCount questions about "$topic".
            Categories: CHARACTER, STORYLINE, UNIVERSE, MOVIE_TV, CREATOR, MIXED_LORE, EVENT, VILLAIN
            Difficulty levels: CASUAL_FAN, HARDCORE_FAN, LORE_MASTER
            Question types: MULTIPLE_CHOICE, IMAGE_BASED, TIMELINE_ORDER, QUOTE_IDENTIFICATION, EVENT_CONNECTION
            
            Make the questions fun, educational, and relevant to comic book lore.
        """.trimIndent()

        return try {
            val response = model.generateContent(prompt)
            val json = extractJson(response.text ?: "")
            gson.fromJson(json, Quiz::class.java)
        } catch (e: Exception) {
            Timber.e(e, "Failed to generate quiz for topic: $topic")
            null
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Lore Card Generation
    // ═══════════════════════════════════════════════════════════════

    suspend fun generateLore(characterName: String, characterId: String): List<LoreCard> {
        val prompt = """
            You are a comic book historian AI. Generate 4 fascinating lore cards about the comic book character "$characterName".
            
            Return ONLY valid JSON array (no markdown, no code blocks) in this exact format:
            [
              {
                "id": "ai_lore_1",
                "title": "Lore card title",
                "content": "Interesting 2-3 sentence fact or story about the character. Keep it engaging and educational.",
                "category": "ORIGIN",
                "relatedEntityId": "$characterId",
                "relatedEntityType": "character",
                "imageUrl": null
              }
            ]
            
            Categories to use: ORIGIN, EASTER_EGG, CREATOR_INSIGHT, RETCON, REAL_WORLD_INFLUENCE, TRIVIA
            
            Make each card reveal a different fascinating aspect:
            1. An origin story detail or key moment
            2. A fun Easter egg or hidden reference
            3. A creator insight or behind-the-scenes fact
            4. A surprising trivia or real-world influence
        """.trimIndent()

        return try {
            val response = model.generateContent(prompt)
            val json = extractJson(response.text ?: "")
            val type = object : TypeToken<List<LoreCard>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            Timber.e(e, "Failed to generate lore for: $characterName")
            emptyList()
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Reading Path Generation
    // ═══════════════════════════════════════════════════════════════

    suspend fun generateReadingPath(
        tags: List<String>,
        userPreferences: String = ""
    ): List<ReadingPath> {
        val tagsStr = tags.joinToString(", ")
        val prompt = """
            You are a comic book reading order expert AI. Generate 3 personalized reading paths based on these preferences:
            Tags/preferences: $tagsStr
            ${if (userPreferences.isNotBlank()) "Additional context: $userPreferences" else ""}
            
            Return ONLY valid JSON array (no markdown, no code blocks) in this exact format:
            [
              {
                "id": "ai_path_1",
                "title": "Reading path title",
                "description": "A compelling 1-2 sentence description of what makes this path special",
                "pathType": "ESSENTIAL",
                "issues": [
                  {
                    "order": 1,
                    "comicIssueId": "issue_id_here",
                    "reason": "Why this issue is included - 1 sentence",
                    "isEssential": true
                  }
                ],
                "estimatedHours": 8,
                "tags": ["Dark", "Cosmic"]
              }
            ]
            
            Path types: ESSENTIAL, FULL_EXPERIENCE, BEGINNER, COMPLETIONIST, EVENT_TIE_IN
            
            For each path, suggest 5-8 real comic book issues/trade paperbacks that match the tags.
            Use real comic book names for the issue IDs (e.g., "batman_year_one", "infinity_gauntlet_1").
            Make the paths diverse — one beginner-friendly, one deep-dive, one event-focused.
        """.trimIndent()

        return try {
            val response = model.generateContent(prompt)
            val json = extractJson(response.text ?: "")
            val type = object : TypeToken<List<ReadingPath>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            Timber.e(e, "Failed to generate reading paths for tags: $tagsStr")
            emptyList()
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Recommended Quizzes Generation
    // ═══════════════════════════════════════════════════════════════

    suspend fun generateRecommendedQuizzes(
        publishers: List<String> = emptyList(),
        mood: String = ""
    ): List<Quiz> {
        val context = buildString {
            if (publishers.isNotEmpty()) append("Favorite publishers: ${publishers.joinToString(", ")}. ")
            if (mood.isNotBlank()) append("Preferred mood: $mood. ")
        }
        val prompt = """
            You are a comic book quiz master AI. Generate 3 fun quiz recommendations for a comic book fan app.
            $context
            
            Return ONLY valid JSON array (no markdown, no code blocks). Each quiz should have 3 questions.
            Use this exact format:
            [
              {
                "id": "ai_rec_quiz_1",
                "title": "Quiz title",
                "category": "CHARACTER",
                "difficulty": "CASUAL_FAN",
                "questions": [
                  {
                    "id": "q1",
                    "type": "MULTIPLE_CHOICE",
                    "question": "Question?",
                    "imageUrl": null,
                    "options": ["A", "B", "C", "D"],
                    "correctAnswerIndex": 0,
                    "explanation": "Explanation",
                    "relatedLoreCardId": null
                  }
                ],
                "relatedEntityId": null,
                "estimatedMinutes": 3,
                "xpReward": 30
              }
            ]
            
            Make the quizzes varied: one about characters, one about storylines, one about creators or universe lore.
        """.trimIndent()

        return try {
            val response = model.generateContent(prompt)
            val json = extractJson(response.text ?: "")
            val type = object : TypeToken<List<Quiz>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            Timber.e(e, "Failed to generate recommended quizzes")
            emptyList()
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Utility
    // ═══════════════════════════════════════════════════════════════

    /** Strip markdown code blocks if present, extract raw JSON */
    private fun extractJson(text: String): String {
        var cleaned = text.trim()
        // Remove ```json ... ``` wrappers
        if (cleaned.startsWith("```")) {
            cleaned = cleaned.substringAfter("\n").substringBeforeLast("```").trim()
        }
        return cleaned
    }
}
