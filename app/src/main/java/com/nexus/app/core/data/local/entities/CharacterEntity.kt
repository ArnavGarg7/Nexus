package com.nexus.app.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val aliases: String,       // JSON array
    val publisher: String,
    val firstAppearance: String,
    val creators: String,      // JSON array
    val synopsis: String,
    val imageUrl: String,
    val coverUrl: String,
    val tags: String,           // JSON array
    val keyStoryArcs: String,   // JSON array
    val relatedCharacterIds: String, // JSON array
    val mediaAppearances: String,    // JSON array
    val updatedAt: Long = System.currentTimeMillis()
)
