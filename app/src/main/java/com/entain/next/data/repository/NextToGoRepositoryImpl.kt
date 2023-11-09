package com.entain.next.data.repository

import com.entain.next.data.local.NextToGoDb
import com.entain.next.data.local.NextToGoEntity
import com.entain.next.data.mapper.toNextToGo
import com.entain.next.data.mapper.toNextToGoEntity
import com.entain.next.data.remote.EntainApi
import com.entain.next.data.util.NUMBER_OF_RACES
import com.entain.next.data.util.REQUEST_METHOD
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.repository.NextToGoRepository
import com.entain.next.domain.util.Resource
import com.entain.next.util.SECONDS
import com.entain.next.util.currentTimeToSeconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NextToGoRepositoryImpl @Inject constructor(
    private val api: EntainApi,
    db: NextToGoDb
) : NextToGoRepository {

    private val localDb = db.nextToGoDao()

    override suspend fun getNextToGoRacingSummery(): Flow<Resource<List<NextToGo>>> {
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

            val remoteData = try {
                api.getNextToGoData(REQUEST_METHOD, NUMBER_OF_RACES)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Can not load racing data"))
                null
            }

            if (remoteData?.isSuccessful == true) {

                localDb.clearAllNextToGo()
                val result =
                    remoteData.body()?.data?.race_summaries?.values?.map { it.toNextToGoEntity() }
                if (result.isNullOrEmpty()) {
                    emit(Resource.Error("Can not load racing data"))
                } else {
                    localDb.insertNextToGoRacing(nextToGoRacing = result)
                    emit(Resource.Success(localDb.getNextToGo().map { it.toNextToGo() }))
                }

                emit(Resource.Loading(false))

            } else {
                emit(Resource.Loading(false))
                emit(Resource.Error("Can not load racing data"))
            }

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

}