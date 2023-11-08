package com.entain.next.domain.model

data class NextToGo(
    val raceId: String,
    val name: String,
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
