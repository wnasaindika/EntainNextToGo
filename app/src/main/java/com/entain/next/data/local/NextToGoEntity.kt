package com.entain.next.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.entain.next.domain.model.Categories

@Entity
data class CachedNextToGo(
    val cachedRaceId:String,
    val cachedMeetingName: String,
    val cachedRaceNumber: String,
    val cachedAdStartTimeInSeconds: Long,
    val cachedAdCategory: Categories,
    @PrimaryKey val id: Int? = null
)