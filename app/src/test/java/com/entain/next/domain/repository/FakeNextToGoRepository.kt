package com.entain.next.domain.repository

import com.entain.next.data.dto.ResponseDto
import com.entain.next.data.local.CachedNextToGo
import com.entain.next.data.mapper.toNextToGo
import com.entain.next.data.mapper.toNextToGoEntity
import com.entain.next.domain.model.NextToGo
import com.entain.next.util.SECONDS
import com.entain.next.util.currentTimeToSeconds
import retrofit2.Response

class FakeNextToGoRepository : NextToGoRepository {

    private val dbData = mutableListOf<CachedNextToGo>()
    private val remoteDate = mutableListOf<Response<ResponseDto>?>(null)

    fun emit(dbList: List<CachedNextToGo>, response: Response<ResponseDto>? = null) {
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
        dbData.remove(nextToGo?.toNextToGo())
    }

    override suspend fun deleteExpiredEvents() {
        val expiredData = dbData
            .filter { (it.cachedAdStartTimeInSeconds - currentTimeToSeconds()) <= -SECONDS }
        expiredData.forEach {
            dbData.remove(it)
        }
    }

    override suspend fun clearCacheAndExtractRemoteData(remoteData: Response<ResponseDto>?): List<CachedNextToGo>? {
        dbData.clear()
        return remoteData?.body()?.data?.race_summaries?.values?.map { it.toNextToGoEntity() }
    }

    override suspend fun getNextToGo(): List<CachedNextToGo>  {
       return dbData 
    }
    override suspend fun insertRemoteDataToLocalCache(nextToGoRacing: List<CachedNextToGo>) {
        dbData.addAll(nextToGoRacing)
    }
}