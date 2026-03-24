package com.nexus.app.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexus.app.core.data.local.dao.CharacterDao
import com.nexus.app.core.data.local.dao.QuizDao
import com.nexus.app.core.data.local.dao.ReadingListDao
import com.nexus.app.core.data.local.entities.CharacterEntity
import com.nexus.app.core.data.local.entities.QuizEntity
import com.nexus.app.core.data.local.entities.QuizResultEntity
import com.nexus.app.core.data.local.entities.ReadingListEntity

@Database(
    entities = [
        CharacterEntity::class,
        QuizEntity::class,
        QuizResultEntity::class,
        ReadingListEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class NexusDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun quizDao(): QuizDao
    abstract fun readingListDao(): ReadingListDao

    companion object {
        const val DATABASE_NAME = "nexus_db"
    }
}
