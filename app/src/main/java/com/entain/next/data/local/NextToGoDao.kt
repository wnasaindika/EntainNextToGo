package com.entain.next.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.entain.next.domain.model.Categories

@Dao
interface NextToGoDao {

    @Query("SELECT * FROM nexttogoentity")
    suspend fun getNextToGo(): List<NextToGoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNextToGoRacing(nextToGoRacing: List<NextToGoEntity>)

    @Query("SELECT * FROM nexttogoentity WHERE id=:id")
    suspend fun getNextToGoByCategory(id: Int): NextToGoEntity

    @Delete
    suspend fun delete(nextToGoEntity: NextToGoEntity)

    @Query("DELETE FROM nexttogoentity")
    suspend fun clearAllNextToGo()
}