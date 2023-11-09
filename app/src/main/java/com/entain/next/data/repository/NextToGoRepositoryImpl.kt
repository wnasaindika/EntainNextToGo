package com.entain.next.data.repository

import com.entain.next.data.dto.ResponseDto
import com.entain.next.data.local.NextToGoDb
import com.entain.next.data.local.NextToGoEntity
import com.entain.next.data.mapper.toNextToGo
import com.entain.next.data.mapper.toNextToGoEntity
import com.entain.next.data.remote.EntainApi
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.repository.NextToGoRepository
import com.entain.next.domain.util.Resource
import com.entain.next.util.SECONDS
import com.entain.next.util.currentTimeToSeconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NextToGoRepositoryImpl @Inject constructor(
    private val api: EntainApi,
    db: NextToGoDb
) : NextToGoRepository {

    private val localDb = db.nextToGoDao()

    override suspend fun fetchNextToGoRacing(): Flow<Resource<List<NextToGo>>> {
        return flow {

            emit(Resource.Loading(true))

            val localData = localDb.getNextToGo()

            val shouldJustLoadFromCache =
                localData.isNotEmpty() && !hasExpiredRacing(localData)

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                emit(Resource.Success(localData.map { it.toNextToGo() }))
                return@flow
            }

            val remoteData = getRemoteData()

            if (remoteData?.isSuccessful == false) {
                emit(Resource.Loading(false))
                emit(Resource.Error("Can not load racing data"))
                return@flow
            }

            val responseResult = clearCacheAndExtractRemoteData(remoteData)

            if (responseResult.isNullOrEmpty()) {
                emit(Resource.Error("Can not load racing data"))
                return@flow
            }

            insertRemoteDataToLocalCache(responseResult)

            emit(Resource.Success(localDb.getNextToGo().map { it.toNextToGo() }))
            emit(Resource.Loading(false))

        }
    }

    override suspend fun clearLocalCache() {
        localDb.clearAllNextToGo()
    }

    override suspend fun deleteExpiredEvent(nextToGo: NextToGo?) {
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

    private fun hasExpiredRacing(racing: List<NextToGoEntity>) =
        racing.all { (it.adStartTimeInSeconds - currentTimeToSeconds()) < -SECONDS }

    private suspend fun getRemoteData(): Response<ResponseDto>? = try {
        api.getNextToGoData(EntainApi.REQUEST_METHOD, EntainApi.NUMBER_OF_RACES)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    private suspend fun clearCacheAndExtractRemoteData(remoteData: Response<ResponseDto>?): List<NextToGoEntity>? {
        localDb.clearAllNextToGo()
        return remoteData?.body()?.data?.race_summaries?.values?.map { it.toNextToGoEntity() }
    }

    private suspend fun insertRemoteDataToLocalCache(nextToGoRacing: List<NextToGoEntity>) {
        localDb.insertNextToGoRacing(nextToGoRacing = nextToGoRacing)
    }


}