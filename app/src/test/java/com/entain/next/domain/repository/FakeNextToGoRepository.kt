package com.entain.next.domain.repository

import com.entain.next.data.dto.ResponseDto
import com.entain.next.data.local.LocalRaceSummery
import com.entain.next.data.mapper.toLocalRaceSummery
import com.entain.next.domain.model.NextToGo
import com.entain.next.util.SECONDS
import com.entain.next.util.currentTimeToSeconds
import retrofit2.Response

class FakeNextToGoRepository : NextToGoRepository {

    private val dbData = mutableListOf<LocalRaceSummery>()
    private val remoteDate = mutableListOf<Response<ResponseDto>?>(null)

    fun emit(dbList: List<LocalRaceSummery>, response: Response<ResponseDto>? = null) {
        dbData.clear()
        remoteDate.clear()
        dbData.addAll(dbList)
        remoteDate.add(response)
    }

    override suspend fun fetchNextToGoRacing(): Response<ResponseDto>? = remoteDate?.first()

    override suspend fun clearLocalCache() {
        dbData.clear()
    }

    override suspend fun deleteExpiredCachedEvent(nextToGo: NextToGo?) {
        dbData.remove(nextToGo?.toLocalRaceSummery())
    }

    override suspend fun deleteExpiredEvents() {
        val expiredData = dbData
            .filter { (it.adStartTimeInSeconds - currentTimeToSeconds()) <= -SECONDS }
        expiredData.forEach {
            dbData.remove(it)
        }
    }

    override suspend fun clearCacheAndExtractRemoteData(remoteData: Response<ResponseDto>?): List<LocalRaceSummery>? {
        dbData.clear()
        return remoteData?.body()?.data?.race_summaries?.values?.map { it.toLocalRaceSummery() }
    }

    override suspend fun getNextToGo(): List<LocalRaceSummery>  {
       return dbData
    }
    override suspend fun insertRemoteDataToLocalCache(nextToGoRacing: List<LocalRaceSummery>) {
        dbData.addAll(nextToGoRacing)
    }
}