package com.entain.next.data.repository

import com.entain.next.data.dto.ResponseDto
import com.entain.next.data.local.LocalRaceSummery
import com.entain.next.data.local.NextToGoDb
import com.entain.next.data.mapper.toNextToGo
import com.entain.next.data.mapper.toNextToGoEntity
import com.entain.next.data.remote.EntainApi
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.repository.NextToGoRepository
import com.entain.next.util.SECONDS
import com.entain.next.util.currentTimeToSeconds
import retrofit2.Response
import javax.inject.Inject

class NextToGoRepositoryImpl @Inject constructor(
    private val api: EntainApi,
    db: NextToGoDb
) : NextToGoRepository {

    private val localDb = db.nextToGoDao()

    override suspend fun fetchNextToGoRacing(): Response<ResponseDto>? = getRemoteData()
    override suspend fun getNextToGo(): List<LocalRaceSummery> {
        return localDb.getNextToGo()
    }

    override suspend fun clearLocalCache() {
        localDb.clearAllNextToGo()
    }

    override suspend fun deleteExpiredCachedEvent(nextToGo: NextToGo?) {
        nextToGo?.toNextToGo()?.let {
            localDb.delete(it)
        }
    }

    override suspend fun deleteExpiredEvents() {
        val expiredData = localDb.getNextToGo()
            .filter { (it.adStartTimeInSeconds - currentTimeToSeconds()) <= -SECONDS }
        expiredData.forEach {
            localDb.delete(it)
        }
    }

   override suspend fun clearCacheAndExtractRemoteData(remoteData: Response<ResponseDto>?): List<LocalRaceSummery>? {
        localDb.clearAllNextToGo()
        return remoteData?.body()?.data?.race_summaries?.values?.map { it.toNextToGoEntity() }
    }

    override suspend fun insertRemoteDataToLocalCache(nextToGoRacing: List<LocalRaceSummery>) {
        localDb.insertNextToGoRacing(nextToGoRacing = nextToGoRacing)
    }

    private suspend fun getRemoteData(): Response<ResponseDto>? = try {
        api.getNextToGoData(EntainApi.REQUEST_METHOD, EntainApi.NUMBER_OF_RACES)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

}