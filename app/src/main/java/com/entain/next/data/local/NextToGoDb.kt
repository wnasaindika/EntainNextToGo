package com.entain.next.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalRaceSummery::class], version = 1)
abstract class NextToGoDb : RoomDatabase() {
    abstract fun nextToGoDao(): NextToGoDao
}