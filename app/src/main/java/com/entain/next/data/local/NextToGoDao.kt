package com.entain.next.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NextToGoDao {

    @Query("SELECT * FROM cachednexttogo")
    suspend fun getNextToGo(): List<CachedNextToGo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNextToGoRacing(nextToGoRacing: List<CachedNextToGo>)

    @Query("SELECT * FROM cachednexttogo WHERE id=:id")
    suspend fun getNextToGoById(id: Int): CachedNextToGo

    @Delete
    suspend fun delete(nextToGoEntity: CachedNextToGo)

    @Query("DELETE FROM cachednexttogo")
    suspend fun clearAllNextToGo()
}