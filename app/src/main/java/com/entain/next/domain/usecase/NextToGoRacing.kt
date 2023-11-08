package com.entain.next.domain.usecase

import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.repository.NextToGoRepository
import com.entain.next.domain.util.Resource
import com.entain.next.presentation.data.RaceOrder
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
        val maxItem = 5
        val horse = sortedList.filter { it.adCategory == Categories.Horse }
        val grayHound = sortedList.filter { it.adCategory == Categories.GrayHound }
        val harness = sortedList.filter { it.adCategory == Categories.Harness }

        return when (raceOrder) {
            RaceOrder.Horse -> horse.take(maxItem)
            RaceOrder.Harness -> harness.take(maxItem)
            RaceOrder.GrayHound -> grayHound.take(maxItem)
            RaceOrder.HorseAndHarness -> {
                (horse + harness).sortedBy { it.adStartTimeInSeconds }.take(maxItem)
            }

            RaceOrder.HorseAndGrayHound -> {
                (horse + grayHound).sortedBy { it.adStartTimeInSeconds }.take(maxItem)
            }

            RaceOrder.HarnessAndGrayHound -> {
                (harness + grayHound).sortedBy { it.adStartTimeInSeconds }.take(maxItem)
            }

            RaceOrder.ALL -> {
                (horse + harness + grayHound).sortedBy { it.adStartTimeInSeconds }.take(maxItem)
            }

            RaceOrder.None -> emptyList()
        }
    }

    suspend fun refresh(order: RaceOrder) {
        nextToGoRepository.clearLocalCache()
        invoke(order)
    }

    suspend fun removeExpiredEventFromCache(nextToGo: NextToGo?) {
        nextToGoRepository.deleteExpiredEvent(nextToGo)
    }
}