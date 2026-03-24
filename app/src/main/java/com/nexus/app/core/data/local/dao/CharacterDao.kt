package com.nexus.app.core.data.local.dao

import androidx.room.*
import com.nexus.app.core.data.local.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters WHERE name LIKE '%' || :query || '%' OR aliases LIKE '%' || :query || '%'")
    fun searchCharacters(query: String): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: String): CharacterEntity?

    @Query("SELECT * FROM characters WHERE publisher = :publisher")
    suspend fun getCharactersByPublisher(publisher: String): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query("DELETE FROM characters WHERE updatedAt < :threshold")
    suspend fun deleteStaleCharacters(threshold: Long)
}
