package com.entain.next.presentation.data

import com.entain.next.domain.model.NextToGo


sealed interface RaceEvent {
    data object Refresh : RaceEvent
    data class ExpiredRace(val selectState: RaceSelectState, val nextToGo: NextToGo) : RaceEvent
    data class SelectRace(val selectState: RaceSelectState) : RaceEvent
}

sealed interface RaceOrder {
    data object Harness : RaceOrder
    data object GrayHound : RaceOrder
    data object Horse : RaceOrder
    data object HorseAndHarness : RaceOrder
    data object HorseAndGrayHound : RaceOrder
    data object HarnessAndGrayHound : RaceOrder
    data object ALL : RaceOrder
    data object None : RaceOrder
}

data class RaceSelectState(
    val horseSelected: Boolean,
    val harnessSelected: Boolean,
    val grayHoundSelected: Boolean
)

