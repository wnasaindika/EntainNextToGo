package com.entain.next.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.entain.next.domain.model.NextToGo
import com.entain.next.presentation.component.RacingFilters
import com.entain.next.presentation.component.RacingItem
import com.entain.next.presentation.data.RaceEvent
import com.entain.next.presentation.data.RaceOrder
import com.entain.next.presentation.data.RaceSelectState
import com.entain.next.presentation.data.UiState
import com.entain.next.presentation.event_mapper.raceSelectionStateMapper
import com.entain.next.ui.Error
import com.entain.next.ui.LoadingState


@Composable
fun NextToGoScreen(vm: EntainViewModel) {
    val state = vm.uiState.collectAsState()
    when (val value = state.value) {
        is UiState.Loading -> LoadingState()
        is UiState.Error -> Error()
        is UiState.Success -> {
            SuccessState(
                value.nextToGoRacing,
                raceSelectionStateMapper(value.raceOrder),
                vm::onRaceEvents
            )
        }
    }
}

@Composable
fun SuccessState(
    nextToGoRacingSummary: List<NextToGo>,
    selected: RaceSelectState,
    onRaceEvent: (RaceEvent) -> Unit
) {

    LazyColumn {
        item {
            RacingFilters(selected) {
                onRaceEvent.invoke(RaceEvent.SelectRace(it))
            }
        }
        nextToGoRacingSummary.forEachIndexed { index, nextToGo ->
            item(key = index) {
                RacingItem(nextToGo = nextToGo) {
                    onRaceEvent.invoke(RaceEvent.ExpiredRace(selected, it))
                }
            }
        }
    }
}

