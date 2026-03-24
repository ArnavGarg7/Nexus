package com.nexus.app.core.data.local.dao

import androidx.room.*
import com.nexus.app.core.data.local.entities.ReadingListEntity

@Dao
interface ReadingListDao {
    @Query("SELECT * FROM reading_lists WHERE userId = :userId ORDER BY savedAt DESC")
    suspend fun getListsForUser(userId: String): List<ReadingListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: ReadingListEntity)

    @Delete
    suspend fun deleteList(list: ReadingListEntity)
}
