package com.entain.next.presentation.event_mapper

import com.entain.next.presentation.data.RaceOrder
import com.entain.next.presentation.data.RaceSelectState

fun raceSelectionStateMapper(raceOrder: RaceOrder): RaceSelectState {
    return when (raceOrder) {
        is RaceOrder.ALL -> RaceCombinations.getAllRacing
        is RaceOrder.HorseAndGrayHound -> RaceCombinations.getHorseAndGrayHound
        is RaceOrder.HorseAndHarness -> RaceCombinations.getHorseAndHarness
        is RaceOrder.HarnessAndGrayHound -> RaceCombinations.getHarnessAndGrayHound
        is RaceOrder.Horse -> RaceCombinations.getHorseRacing
        is RaceOrder.Harness -> RaceCombinations.getHarnessRacing
        is RaceOrder.GrayHound -> RaceCombinations.getGrayHoundRacing
        is RaceOrder.None -> RaceCombinations.getNoRaceSelected
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