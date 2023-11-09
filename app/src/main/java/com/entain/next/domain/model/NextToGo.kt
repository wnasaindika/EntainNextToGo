package com.entain.next.domain.model

data class NextToGo(
    val raceId: String,
    val meetingName: String,
    val raceNumber: String,
    val adStartTimeInSeconds: Long,
    val adCategory: Categories
)

enum class Categories {
    Horse,
    GrayHound,
    Harness,
    None
}
