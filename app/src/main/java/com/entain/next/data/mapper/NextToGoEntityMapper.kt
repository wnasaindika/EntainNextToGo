package com.entain.next.data.mapper

import com.entain.next.data.dto.RaceSummaryDto
import com.entain.next.data.local.CachedNextToGo
import com.entain.next.data.util.grayHound
import com.entain.next.data.util.harness
import com.entain.next.data.util.horse
import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo


fun RaceSummaryDto.toNextToGoEntity(): CachedNextToGo {
    return CachedNextToGo(
        cachedRaceId = this.race_id,
        cachedMeetingName = this.meeting_name,
        cachedRaceNumber = this.race_number,
        cachedAdCategory = when (this.category_id) {
            grayHound -> Categories.GrayHound
            horse -> Categories.Horse
            harness -> Categories.Harness
            else -> Categories.None
        },
        cachedAdStartTimeInSeconds = this.advertised_start.seconds
    )
}

fun CachedNextToGo.toNextToGo(): NextToGo {
    return NextToGo(
        raceId = this.cachedRaceId,
        meetingName = this.cachedMeetingName,
        raceNumber = this.cachedRaceNumber,
        adCategory = this.cachedAdCategory,
        adStartTimeInSeconds = this.cachedAdStartTimeInSeconds
    )
}

fun NextToGo.toNextToGo(): CachedNextToGo {
    return CachedNextToGo(
        cachedRaceId = this.raceId,
        cachedMeetingName = this.meetingName,
        cachedRaceNumber = this.raceNumber,
        cachedAdCategory = this.adCategory,
        cachedAdStartTimeInSeconds = this.adStartTimeInSeconds
    )
}