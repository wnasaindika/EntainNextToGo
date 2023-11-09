package com.entain.next.presentation.data

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