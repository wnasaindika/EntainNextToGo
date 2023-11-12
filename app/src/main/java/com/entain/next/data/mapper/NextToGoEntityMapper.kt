package com.entain.next.data.mapper

import com.entain.next.data.dto.RaceSummaryDto
import com.entain.next.data.local.LocalRaceSummery
import com.entain.next.data.util.grayHound
import com.entain.next.data.util.harness
import com.entain.next.data.util.horse
import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo


fun RaceSummaryDto.toNextToGoEntity(): LocalRaceSummery {
    return LocalRaceSummery(
        raceId = this.race_id,
        meetingName = this.meeting_name,
        raceNumber = this.race_number,
        adCategory = when (this.category_id) {
            grayHound -> Categories.GrayHound
            horse -> Categories.Horse
            harness -> Categories.Harness
            else -> Categories.None
        },
        adStartTimeInSeconds = this.advertised_start.seconds
    )
}

fun LocalRaceSummery.toNextToGo(): NextToGo {
    return NextToGo(
        raceId = this.raceId,
        meetingName = this.meetingName,
        raceNumber = this.raceNumber,
        adCategory = this.adCategory,
        adStartTimeInSeconds = this.adStartTimeInSeconds
    )
}

fun NextToGo.toNextToGo(): LocalRaceSummery {
    return LocalRaceSummery(
        raceId = this.raceId,
        meetingName = this.meetingName,
        raceNumber = this.raceNumber,
        adCategory = this.adCategory,
        adStartTimeInSeconds = this.adStartTimeInSeconds
    )
}