package com.entain.next.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.entain.next.domain.model.Categories

@Entity
data class NextToGoEntity(
    val raceId:String,
    val meetingName: String,
    val raceNumber: String,
    val adStartTimeInSeconds: Long,
    val adCategory: Categories,
    @PrimaryKey val id: Int? = null
)