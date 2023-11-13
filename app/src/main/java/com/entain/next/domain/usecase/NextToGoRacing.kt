package com.entain.next.domain.usecase

import com.entain.next.data.local.LocalRaceSummery
import com.entain.next.data.mapper.toLocalRaceSummery
import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.repository.NextToGoRepository
import com.entain.next.domain.util.Resource
import com.entain.next.presentation.data.RaceOrder
import com.entain.next.util.MAX_ITEMS_IN_LIST
import com.entain.next.util.MIN_RACES_FOR_REFRESH
import com.entain.next.util.SECONDS
import com.entain.next.util.currentTimeToSeconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NextToGoRacing @Inject constructor(private val nextToGoRepository: NextToGoRepository) {

    suspend operator fun invoke(raceOrder: RaceOrder): Flow<Resource<List<NextToGo>>> {

        return flow {
            emit(Resource.Loading(true))
            val localData = nextToGoRepository.getNextToGo()
            if (shouldLoadFromCache(localData)) {
                emit(Resource.Loading(false))
                emit(Resource.Success(getSortedList(raceOrder, localData.map { it.toLocalRaceSummery() })))
                return@flow
            }

            val remoteData = nextToGoRepository.fetchNextToGoRacing()

            if (remoteData?.isSuccessful == false) {
                emit(Resource.Loading(false))
                emit(Resource.Error("Can not load racing data"))
                return@flow
            }

            val responseResult = nextToGoRepository.clearCacheAndExtractRemoteData(remoteData)

            if (responseResult.isNullOrEmpty() || responseResult.count() < MIN_RACES_FOR_REFRESH) {
                emit(Resource.Loading(false))
                emit(Resource.Error("Can not load racing data"))
                return@flow
            }

            nextToGoRepository.insertRemoteDataToLocalCache(responseResult)
            val freshDataFromLocalData = nextToGoRepository.getNextToGo()

            emit(
                Resource.Success(
                    getSortedList(
                        raceOrder,
                        freshDataFromLocalData.map { it.toLocalRaceSummery() })
                )
            )
            emit(Resource.Loading(false))
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
        nextToGoRepository.deleteExpiredCachedEvent(nextToGo)
    }

    suspend fun checkAndRemoveExpiredEvents() {
        nextToGoRepository.deleteExpiredEvents()
    }

    private fun hasExpiredRacing(racing: List<LocalRaceSummery>) =
        racing.all { (it.adStartTimeInSeconds - currentTimeToSeconds()) < -SECONDS }

    private fun shouldLoadFromCache(localData: List<LocalRaceSummery>) =
        localData.isNotEmpty() && !hasExpiredRacing(localData) && localData.count() > MIN_RACES_FOR_REFRESH
}