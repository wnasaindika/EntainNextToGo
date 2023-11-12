package com.entain.next.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NextToGoDao {

    @Query("SELECT * FROM localracesummery")
    suspend fun getNextToGo(): List<LocalRaceSummery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNextToGoRacing(nextToGoRacing: List<LocalRaceSummery>)

    @Query("SELECT * FROM localracesummery WHERE id=:id")
    suspend fun getNextToGoById(id: Int): LocalRaceSummery

    @Delete
    suspend fun delete(nextToGoEntity: LocalRaceSummery)

    @Query("DELETE FROM localracesummery")
    suspend fun clearAllNextToGo()
}