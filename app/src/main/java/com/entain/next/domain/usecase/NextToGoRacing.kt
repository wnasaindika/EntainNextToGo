package com.entain.next.domain.usecase

import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.repository.NextToGoRepository
import com.entain.next.domain.util.Resource
import com.entain.next.presentation.data.RaceOrder
import com.entain.next.util.MAX_ITEMS_IN_LIST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NextToGoRacing @Inject constructor(private val nextToGoRepository: NextToGoRepository) {

    suspend operator fun invoke(raceOrder: RaceOrder): Flow<Resource<List<NextToGo>>> {

        return nextToGoRepository.getNextToGoRacingSummery().map {

            val sortedList = it.data?.sortedBy { it.adStartTimeInSeconds } ?: listOf()
            when (it) {
                is Resource.Success -> {
                    Resource.Success(getSortedList(raceOrder, sortedList))
                }

                else -> it
            }
        }
    }

    private fun getSortedList(raceOrder: RaceOrder, sortedList: List<NextToGo>): List<NextToGo> {
        val horse = sortedList.filter { it.adCategory == Categories.Horse }
        val grayHound = sortedList.filter { it.adCategory == Categories.GrayHound }
        val harness = sortedList.filter { it.adCategory == Categories.Harness }

        return when (raceOrder) {
            RaceOrder.Horse -> horse.take(MAX_ITEMS_IN_LIST)
            RaceOrder.Harness -> harness.take(MAX_ITEMS_IN_LIST)
            RaceOrder.GrayHound -> grayHound.take(MAX_ITEMS_IN_LIST)
            RaceOrder.HorseAndHarness -> {
                (horse + harness).sortedBy { it.adStartTimeInSeconds }.take(MAX_ITEMS_IN_LIST)
            }

            RaceOrder.HorseAndGrayHound -> {
                (horse + grayHound).sortedBy { it.adStartTimeInSeconds }.take(MAX_ITEMS_IN_LIST)
            }

            RaceOrder.HarnessAndGrayHound -> {
                (harness + grayHound).sortedBy { it.adStartTimeInSeconds }.take(MAX_ITEMS_IN_LIST)
            }

            RaceOrder.ALL -> {
                (horse + harness + grayHound).sortedBy { it.adStartTimeInSeconds }
                    .take(MAX_ITEMS_IN_LIST)
            }

            RaceOrder.None -> emptyList()
        }
    }

    suspend fun refresh() {
        nextToGoRepository.clearLocalCache()
    }

    suspend fun removeExpiredEventFromCache(nextToGo: NextToGo?) {
        nextToGoRepository.deleteExpiredEvent(nextToGo)
    }

    suspend fun checkAndRemoveExpiredEvents() {
        nextToGoRepository.deleteExpiredEvents()
    }
}