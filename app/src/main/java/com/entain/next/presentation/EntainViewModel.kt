package com.entain.next.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entain.next.domain.usecase.NextToGoRacing
import com.entain.next.domain.util.Resource
import com.entain.next.presentation.data.RaceEvent
import com.entain.next.presentation.data.RaceOrder
import com.entain.next.presentation.data.UiState
import com.entain.next.presentation.event_mapper.RaceCombinations
import com.entain.next.presentation.event_mapper.raceOrderMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntainViewModel @Inject constructor(private val nextToGoRacingImpl: NextToGoRacing) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    init {
        onRaceEvents(RaceEvent.SelectRace(RaceCombinations.getAllRacing))
    }

    fun onRaceEvents(event: RaceEvent) {
        viewModelScope.launch {
            nextToGoRacingImpl.checkAndRemoveExpiredEvents()
            when (event) {
                is RaceEvent.Refresh -> {
                    nextToGoRacingImpl.refresh()
                    fetchRacingData(RaceOrder.ALL)
                }

                is RaceEvent.SelectRace -> {
                    fetchRacingData(raceOrder = raceOrderMapper(event.selectState))
                }

                is RaceEvent.ExpiredRace -> {
                    nextToGoRacingImpl.removeExpiredEventFromCache(event.nextToGo)
                    fetchRacingData(raceOrder = raceOrderMapper(event.selectState))
                }
            }
        }
    }

    private suspend fun fetchRacingData(raceOrder: RaceOrder) {
        nextToGoRacingImpl.invoke(raceOrder).onEach {
            when (it) {
                is Resource.Loading -> {
                    if (it.isLoading)
                        _uiState.emit(UiState.Loading)
                }

                is Resource.Error -> {
                    _uiState.emit(UiState.Error)
                }

                is Resource.Success -> {
                    _uiState.emit(UiState.Success(it.data ?: emptyList(), raceOrder))
                }
            }
        }.collect()
    }
}