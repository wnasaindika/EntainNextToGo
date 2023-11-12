package com.entain.next.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.entain.next.domain.model.NextToGo
import com.entain.next.presentation.component.RacingFilters
import com.entain.next.presentation.component.RacingItem
import com.entain.next.presentation.data.RaceEvent
import com.entain.next.presentation.data.RaceSelectState
import com.entain.next.presentation.data.UiState
import com.entain.next.presentation.event_mapper.raceSelectionStateMapper
import com.entain.next.ui.Error
import com.entain.next.ui.LoadingState
import com.entain.next.util.SECOND_IN_MILL_SECOND
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun NextToGoScreen(viewModel: EntainViewModel = hiltViewModel()) {

    when (val state = viewModel.uiState.collectAsState().value) {
        is UiState.Loading -> LoadingState()
        is UiState.Error -> Error()
        is UiState.Success -> {
            SuccessState(
                state.nextToGoRacing,
                raceSelectionStateMapper(state.raceOrder),
                viewModel::onRaceEvents
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SuccessState(
    nextToGoRacingSummary: List<NextToGo>,
    selected: RaceSelectState,
    onRaceEvent: (RaceEvent) -> Unit
) {
    Box {
        var isRefreshing by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        fun refresh() {
            coroutineScope.launch {
                isRefreshing = true
                delay(SECOND_IN_MILL_SECOND)
                onRaceEvent.invoke(RaceEvent.Refresh)
                isRefreshing = false
            }
        }


        val state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = ::refresh)
        LazyColumn(modifier = Modifier.pullRefresh(state)) {
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

        PullRefreshIndicator(
            refreshing = isRefreshing, state = state, modifier = Modifier.align(
                Alignment.TopCenter
            )
        )
    }

}

