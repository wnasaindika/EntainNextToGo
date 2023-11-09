package com.entain.next.presentation.event_mapper

import com.entain.next.presentation.data.RaceSelectState

object RaceCombinations {
    val getAllRacing: RaceSelectState
        get() = RaceSelectState(
            horseSelected = true,
            grayHoundSelected = true,
            harnessSelected = true
        )
    val getHarnessRacing: RaceSelectState
        get() = RaceSelectState(
            horseSelected = false,
            grayHoundSelected = false,
            harnessSelected = true
        )
    val getGrayHoundRacing: RaceSelectState
        get() = RaceSelectState(
            horseSelected = false,
            grayHoundSelected = true,
            harnessSelected = false
        )
    val getHorseRacing: RaceSelectState
        get() = RaceSelectState(
            horseSelected = true,
            grayHoundSelected = false,
            harnessSelected = false
        )
    val getHarnessAndGrayHound: RaceSelectState
        get() = RaceSelectState(
            horseSelected = false,
            grayHoundSelected = true,
            harnessSelected = true
        )

    val getHorseAndGrayHound: RaceSelectState
        get() = RaceSelectState(
            horseSelected = true,
            grayHoundSelected = true,
            harnessSelected = false
        )
    val getHorseAndHarness: RaceSelectState
        get() = RaceSelectState(
            horseSelected = true,
            grayHoundSelected = false,
            harnessSelected = true
        )

    val getNoRaceSelected: RaceSelectState
        get() = RaceSelectState(
            horseSelected = false,
            grayHoundSelected = false,
            harnessSelected = false
        )

}