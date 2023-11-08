package com.entain.next.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.usecase.NextToGoRacing
import com.entain.next.domain.util.Resource
import com.entain.next.presentation.data.RaceEvent
import com.entain.next.presentation.data.RaceOrder
import com.entain.next.presentation.data.UiState
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
class EntainViewModel @Inject constructor(private val nextToGoRacing: NextToGoRacing) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    init {
        fetchRacingData(RaceOrder.ALL, null)
    }

    fun onRaceEvents(event: RaceEvent) {
        when (event) {
            is RaceEvent.Refresh -> {
                viewModelScope.launch {
                    nextToGoRacing.refresh(RaceOrder.ALL)
                }
            }

            is RaceEvent.SelectRace -> {
                fetchRacingData(raceOrder = raceOrderMapper(event.selectState), null)
            }

            is RaceEvent.ExpiredRace -> {
                fetchRacingData(raceOrder = raceOrderMapper(event.selectState), event.nextToGo)
            }
        }
    }

    private fun fetchRacingData(raceOrder: RaceOrder, nextToGo: NextToGo?) {
        viewModelScope.launch {
            nextToGoRacing.removeExpiredEventFromCache(nextToGo)
            nextToGoRacing.invoke(raceOrder).onEach {
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

}