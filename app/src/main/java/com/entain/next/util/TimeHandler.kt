package com.entain.next.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class TimeHandler(private val time: Long) {

    private var timeIncrement = time
    private var shouldContinue = true
    suspend fun handleTime() = flow {
        while (timeIncrement > -SECONDS && shouldContinue) {
            delay(SECOND_IN_MILL_SECOND)
            timeIncrement--
            emit(
                TimeEvent.Progress(
                    (timeIncrement % MIN_IN_SECOND) / SECONDS,
                    timeIncrement % SECONDS
                )
            )
        }
        if (timeIncrement == -SECONDS) {
            shouldContinue = false
            emit(TimeEvent.Expired)
        }
    }
}

sealed interface TimeEvent {
    data class Progress(val m: Long, val s: Long) : TimeEvent
    data object Expired : TimeEvent
}