package com.nexus.app.core.data.remote.dto

import com.nexus.app.core.data.local.entities.CharacterEntity
import com.nexus.app.domain.model.*

// DTO -> Domain
fun CharacterDto.toDomain() = Character(
    id = id, name = name, aliases = aliases,
    publisher = Publisher.valueOf(publisher.uppercase()),
    firstAppearance = firstAppearance, creators = creators,
    synopsis = synopsis, imageUrl = imageUrl, coverUrl = coverUrl,
    tags = tags, keyStoryArcs = keyStoryArcs,
    relatedCharacterIds = relatedCharacterIds, mediaAppearances = mediaAppearances
)

fun ReadingPathDto.toDomain() = ReadingPath(
    id = id, title = title, description = description,
    pathType = PathType.valueOf(pathType.uppercase()),
    issues = issues.map { ReadingPathItem(it.order, it.comicIssueId, it.reason, it.isEssential) },
    estimatedHours = estimatedHours, tags = tags
)

fun StoryArcDto.toDomain() = StoryArc(
    id = id, title = title, publisher = Publisher.valueOf(publisher.uppercase()),
    issueIds = issueIds, essentialIssueIds = essentialIssueIds, writer = writer,
    startYear = startYear, endYear = endYear, synopsis = synopsis,
    coverImageUrl = coverImageUrl, tags = tags, isEvent = isEvent,
    preEventArcIds = preEventArcIds, postEventArcIds = postEventArcIds
)

fun MediaItemDto.toDomain() = MediaItem(
    id = id, title = title, type = MediaType.valueOf(type.uppercase()),
    studio = studio, releaseYear = releaseYear, synopsis = synopsis,
    posterUrl = posterUrl, relatedComicIds = relatedComicIds,
    characterIds = characterIds, tags = tags
)

fun LoreCardDto.toDomain() = LoreCard(
    id = id, title = title, content = content,
    category = LoreCategory.valueOf(category.uppercase()),
    relatedEntityId = relatedEntityId, relatedEntityType = relatedEntityType,
    imageUrl = imageUrl
)

fun QuizDto.toDomain() = Quiz(
    id = id, title = title,
    category = QuizCategory.valueOf(category.uppercase()),
    difficulty = QuizDifficulty.valueOf(difficulty.uppercase()),
    questions = questions.map {
        QuizQuestion(
            id = it.id, type = QuestionType.valueOf(it.type.uppercase()),
            question = it.question, imageUrl = it.imageUrl,
            options = it.options, correctAnswerIndex = it.correctAnswerIndex,
            explanation = it.explanation, relatedLoreCardId = it.relatedLoreCardId
        )
    },
    relatedEntityId = relatedEntityId, estimatedMinutes = estimatedMinutes, xpReward = xpReward
)

// Entity -> Domain
fun CharacterEntity.toDomain() = Character(
    id = id, name = name,
    aliases = aliases.split(",").filter { it.isNotBlank() },
    publisher = Publisher.valueOf(publisher.uppercase()),
    firstAppearance = firstAppearance,
    creators = creators.split(",").filter { it.isNotBlank() },
    synopsis = synopsis, imageUrl = imageUrl, coverUrl = coverUrl,
    tags = tags.split(",").filter { it.isNotBlank() },
    keyStoryArcs = keyStoryArcs.split(",").filter { it.isNotBlank() },
    relatedCharacterIds = relatedCharacterIds.split(",").filter { it.isNotBlank() },
    mediaAppearances = mediaAppearances.split(",").filter { it.isNotBlank() }
)
