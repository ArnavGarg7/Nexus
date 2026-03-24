package com.nexus.app.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    val id: String,
    val name: String,
    val aliases: List<String>,
    val publisher: String,
    @SerializedName("first_appearance") val firstAppearance: String,
    val creators: List<String>,
    val synopsis: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("cover_url") val coverUrl: String,
    val tags: List<String>,
    @SerializedName("key_story_arcs") val keyStoryArcs: List<String>,
    @SerializedName("related_character_ids") val relatedCharacterIds: List<String>,
    @SerializedName("media_appearances") val mediaAppearances: List<String>
)

data class ReadingPathDto(
    val id: String,
    val title: String,
    val description: String,
    @SerializedName("path_type") val pathType: String,
    val issues: List<ReadingPathItemDto>,
    @SerializedName("estimated_hours") val estimatedHours: Int,
    val tags: List<String>
)

data class ReadingPathItemDto(
    val order: Int,
    @SerializedName("comic_issue_id") val comicIssueId: String,
    val reason: String,
    @SerializedName("is_essential") val isEssential: Boolean
)

data class StoryArcDto(
    val id: String,
    val title: String,
    val publisher: String,
    @SerializedName("issue_ids") val issueIds: List<String>,
    @SerializedName("essential_issue_ids") val essentialIssueIds: List<String>,
    val writer: String,
    @SerializedName("start_year") val startYear: Int,
    @SerializedName("end_year") val endYear: Int?,
    val synopsis: String,
    @SerializedName("cover_image_url") val coverImageUrl: String,
    val tags: List<String>,
    @SerializedName("is_event") val isEvent: Boolean,
    @SerializedName("pre_event_arc_ids") val preEventArcIds: List<String>,
    @SerializedName("post_event_arc_ids") val postEventArcIds: List<String>
)

data class MediaItemDto(
    val id: String,
    val title: String,
    val type: String,
    val studio: String,
    @SerializedName("release_year") val releaseYear: Int,
    val synopsis: String,
    @SerializedName("poster_url") val posterUrl: String,
    @SerializedName("related_comic_ids") val relatedComicIds: List<String>,
    @SerializedName("character_ids") val characterIds: List<String>,
    val tags: List<String>
)

data class LoreCardDto(
    val id: String,
    val title: String,
    val content: String,
    val category: String,
    @SerializedName("related_entity_id") val relatedEntityId: String,
    @SerializedName("related_entity_type") val relatedEntityType: String,
    @SerializedName("image_url") val imageUrl: String?
)

data class QuizDto(
    val id: String,
    val title: String,
    val category: String,
    val difficulty: String,
    val questions: List<QuizQuestionDto>,
    @SerializedName("related_entity_id") val relatedEntityId: String?,
    @SerializedName("estimated_minutes") val estimatedMinutes: Int,
    @SerializedName("xp_reward") val xpReward: Int
)

data class QuizQuestionDto(
    val id: String,
    val type: String,
    val question: String,
    @SerializedName("image_url") val imageUrl: String?,
    val options: List<String>,
    @SerializedName("correct_answer_index") val correctAnswerIndex: Int,
    val explanation: String,
    @SerializedName("related_lore_card_id") val relatedLoreCardId: String?
)

data class GenerateQuizRequest(
    @SerializedName("entity_id") val entityId: String,
    val difficulty: String
)

data class QuizResultRequest(
    @SerializedName("user_id") val userId: String,
    val score: Int,
    @SerializedName("xp_earned") val xpEarned: Int
)
