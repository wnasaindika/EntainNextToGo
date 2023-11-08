package com.entain.next.data.dto


data class ResponseDto(
    val status: Int,
    val message: String,
    val data: NextToGoDto?
)

data class NextToGoDto(
    val next_to_go_ids: List<String>?,
    val race_summaries: Map<String, RaceSummaryDto>?
)

data class RaceSummaryDto(
    val race_id: String,
    val meeting_name: String,
    val category_id: String,
    val race_number: String,
    val advertised_start: AdvertisedStartDto
)

data class AdvertisedStartDto(val seconds: Long)




