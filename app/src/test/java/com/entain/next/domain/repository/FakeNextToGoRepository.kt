package com.entain.next.domain.repository

import com.entain.next.data.dto.NextToGoDto
import com.entain.next.data.dto.ResponseDto
import com.entain.next.data.local.NextToGoEntity
import com.entain.next.data.mapper.toNextToGo
import com.entain.next.data.mapper.toNextToGoEntity
import com.entain.next.data.remote.EntainApi
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.util.Resource
import com.entain.next.util.SECONDS
import com.entain.next.util.currentTimeToSeconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeNextToGoRepository : NextToGoRepository {

    private val dbData = mutableListOf<NextToGoEntity>()
    private val remoteDate = mutableListOf<Response<ResponseDto>>(
        Response.success(
            ResponseDto(
                status = 200, message = "test0", data = NextToGoDto(
                    next_to_go_ids = listOf(),
                    mapOf()
                )
            )
        )
    )

    override suspend fun fetchNextToGoRacing(): Flow<Resource<List<NextToGo>>> {
        return flow {
            emit(Resource.Loading(true))
            val localData = dbData
            val shouldJustLoadFromCache =
                localData.isNotEmpty() && !hasExpiredRacing(localData) && localData.count() > 5

            if (shouldJustLoadFromCache) {
                emit(Resource.Success(localData.map { it.toNextToGo() }))
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteData = getRemoteData()

            if (remoteData?.isSuccessful == false) {
                emit(Resource.Loading(false))
                emit(Resource.Error("Can not load racing data"))
                return@flow
            }

            val responseResult = clearCacheAndExtractRemoteData(remoteData)

            if (responseResult.isNullOrEmpty() || responseResult.count() < 5) {
                emit(Resource.Loading(false))
                emit(Resource.Error("Can not load racing data"))
                return@flow
            }

            insertRemoteDataToLocalCache(responseResult)

            emit(Resource.Success(dbData.map { it.toNextToGo() }))
            emit(Resource.Loading(false))
        }
    }

    fun emit(dbList: List<NextToGoEntity>, response: Response<ResponseDto>) {
        dbData.clear()
        remoteDate.clear()
        dbData.addAll(dbList)
        remoteDate.add(response)
    }

    override suspend fun clearLocalCache() {
        dbData.clear()
    }

    override suspend fun deleteExpiredEvent(nextToGo: NextToGo?) {
        dbData.remove(nextToGo?.toNextToGo())
    }

    override suspend fun deleteExpiredEvents() {
        val expiredData = dbData
            .filter { (it.adStartTimeInSeconds - currentTimeToSeconds()) <= -SECONDS }
        expiredData.forEach {
            dbData.remove(it)
        }
    }

    private fun hasExpiredRacing(racing: List<NextToGoEntity>) =
        racing.all { (it.adStartTimeInSeconds - currentTimeToSeconds()) < -SECONDS }

    private fun getRemoteData(): Response<ResponseDto>? = try {
        remoteDate.first()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    private fun clearCacheAndExtractRemoteData(remoteData: Response<ResponseDto>?): List<NextToGoEntity>? {
        dbData.clear()
        return remoteData?.body()?.data?.race_summaries?.values?.map { it.toNextToGoEntity() }
    }

    private fun insertRemoteDataToLocalCache(nextToGoRacing: List<NextToGoEntity>) {
        dbData.addAll(nextToGoRacing)
    }
}