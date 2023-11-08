package com.entain.next.presentation.event_mapper

import com.entain.next.presentation.data.RaceOrder
import com.entain.next.presentation.data.RaceSelectState

fun raceSelectionStateMapper(raceOrder: RaceOrder): RaceSelectState {
    return when (raceOrder) {
        is RaceOrder.ALL -> RaceSelectState(
            horseSelected = true,
            grayHoundSelected = true,
            harnessSelected = true
        )

        is RaceOrder.HorseAndGrayHound -> RaceSelectState(
            horseSelected = true,
            grayHoundSelected = true,
            harnessSelected = false
        )

        is RaceOrder.HorseAndHarness -> RaceSelectState(
            horseSelected = true,
            grayHoundSelected = false,
            harnessSelected = true
        )

        is RaceOrder.HarnessAndGrayHound -> RaceSelectState(
            horseSelected = false,
            grayHoundSelected = true,
            harnessSelected = true
        )

        is RaceOrder.Horse -> RaceSelectState(
            horseSelected = true,
            grayHoundSelected = false,
            harnessSelected = false
        )

        is RaceOrder.Harness -> RaceSelectState(
            horseSelected = false,
            grayHoundSelected = false,
            harnessSelected = true
        )

        is RaceOrder.GrayHound -> RaceSelectState(
            horseSelected = false,
            grayHoundSelected = true,
            harnessSelected = false
        )

        is RaceOrder.None -> RaceSelectState(
            horseSelected = false,
            grayHoundSelected = false,
            harnessSelected = false
        )
    }
}


fun raceOrderMapper(selectedState: RaceSelectState): RaceOrder {
    return when {
        selectedState.harnessSelected && selectedState.grayHoundSelected && selectedState.horseSelected -> RaceOrder.ALL
        selectedState.harnessSelected && selectedState.grayHoundSelected -> RaceOrder.HarnessAndGrayHound

        selectedState.harnessSelected && selectedState.horseSelected -> RaceOrder.HorseAndHarness

        selectedState.grayHoundSelected && selectedState.horseSelected -> RaceOrder.HorseAndGrayHound

        selectedState.harnessSelected -> RaceOrder.Harness
        selectedState.grayHoundSelected -> RaceOrder.GrayHound
        selectedState.horseSelected -> RaceOrder.Horse
        else -> RaceOrder.None
    }
}