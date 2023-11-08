package com.entain.next.presentation.data

import com.entain.next.domain.model.NextToGo

sealed interface UiState {
    data object Loading : UiState
    data object Error : UiState
    data class Success(val nextToGoRacing: List<NextToGo>, val raceOrder: RaceOrder) : UiState
}