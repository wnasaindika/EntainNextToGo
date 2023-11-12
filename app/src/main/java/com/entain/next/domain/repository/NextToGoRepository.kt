package com.entain.next.domain.repository

import com.entain.next.data.dto.ResponseDto
import com.entain.next.data.local.LocalRaceSummery
import com.entain.next.domain.model.NextToGo
import retrofit2.Response

interface NextToGoRepository {
    suspend fun fetchNextToGoRacing(): Response<ResponseDto>?
    suspend fun clearLocalCache()
    suspend fun deleteExpiredCachedEvent(nextToGo: NextToGo?)
    suspend fun deleteExpiredEvents()
    suspend fun clearCacheAndExtractRemoteData(remoteData: Response<ResponseDto>?): List<LocalRaceSummery>?
    suspend fun getNextToGo(): List<LocalRaceSummery>
    suspend fun insertRemoteDataToLocalCache(nextToGoRacing: List<LocalRaceSummery>)
}