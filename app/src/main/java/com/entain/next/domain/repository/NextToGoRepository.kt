package com.entain.next.domain.repository

import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface NextToGoRepository {
    suspend fun fetchNextToGoRacing(): Flow<Resource<List<NextToGo>>>
    suspend fun clearLocalCache()
    suspend fun deleteExpiredEvent(nextToGo: NextToGo?)
    suspend fun  deleteExpiredEvents()
}