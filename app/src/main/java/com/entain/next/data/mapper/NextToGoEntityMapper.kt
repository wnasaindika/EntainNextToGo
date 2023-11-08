package com.entain.next.data.mapper

import com.entain.next.data.dto.RaceSummaryDto
import com.entain.next.data.local.NextToGoEntity
import com.entain.next.data.util.grayHound
import com.entain.next.data.util.harness
import com.entain.next.data.util.horse
import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo


fun RaceSummaryDto.toNextToGoEntity(): NextToGoEntity {
    return NextToGoEntity(
        raceId = this.race_id,
        name = this.meeting_name,
        raceNumber = this.race_number,
        adCategory = when (this.category_id) {
            grayHound -> Categories.GrayHound
            horse -> Categories.Horse
            harness -> Categories.GrayHound
            else -> Categories.None
        },
        adStartTimeInSeconds = this.advertised_start.seconds
    )
}

fun NextToGoEntity.toNextToGo(): NextToGo {
    return NextToGo(
        raceId = this.raceId,
        name = this.name,
        raceNumber = this.raceNumber,
        adCategory = this.adCategory,
        adStartTimeInSeconds = this.adStartTimeInSeconds
    )
}

fun NextToGo.toNextToGo(): NextToGoEntity {
    return NextToGoEntity(
        raceId = this.raceId,
        name = this.name,
        raceNumber = this.raceNumber,
        adCategory = this.adCategory,
        adStartTimeInSeconds = this.adStartTimeInSeconds
    )
}