package com.nexus.app.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_lists")
data class ReadingListEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,
    val pathJson: String,  // Serialized ReadingPath
    val savedAt: Long = System.currentTimeMillis()
)
