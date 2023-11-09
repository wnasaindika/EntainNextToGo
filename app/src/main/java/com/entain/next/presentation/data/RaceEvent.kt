package com.entain.next.presentation.data

import com.entain.next.domain.model.NextToGo


sealed interface RaceEvent {
    data object Refresh : RaceEvent
    data class ExpiredRace(val selectState: RaceSelectState, val nextToGo: NextToGo) : RaceEvent
    data class SelectRace(val selectState: RaceSelectState) : RaceEvent
}
