package com.entain.next.di

import com.entain.next.data.dto.AdvertisedStartDto
import com.entain.next.data.dto.NextToGoDto
import com.entain.next.data.dto.RaceSummaryDto
import com.entain.next.data.dto.ResponseDto
import com.entain.next.data.local.CachedNextToGo
import com.entain.next.data.mapper.toNextToGo
import com.entain.next.data.mapper.toNextToGoEntity
import com.entain.next.data.util.grayHound
import com.entain.next.data.util.harness
import com.entain.next.data.util.horse
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.repository.NextToGoRepository
import com.entain.next.util.SECONDS
import com.entain.next.util.SECOND_IN_MILL_SECOND
import com.entain.next.util.currentTimeToSeconds
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import javax.inject.Inject

class FakeNextToGoRepository @Inject constructor() : NextToGoRepository {
    private val dbData = mutableListOf<CachedNextToGo>()
    private val remoteData = MutableStateFlow<Response<ResponseDto>?>(null)

    init {
        emit(fakeRemoteDate())
    }

    private fun emit(stab: Response<ResponseDto>?) {
        dbData.clear()
        dbData.addAll(stab?.body()?.data?.race_summaries?.values?.map { it.toNextToGoEntity() }
            ?: listOf())
    }

    override suspend fun fetchNextToGoRacing(): Response<ResponseDto>? = remoteData.value

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

    override suspend fun getNextToGo(): List<CachedNextToGo> {
        return dbData
    }

    override suspend fun insertRemoteDataToLocalCache(nextToGoRacing: List<CachedNextToGo>) {
        dbData.addAll(nextToGoRacing)
    }

    private fun fakeRemoteDate(): Response<ResponseDto> {
        val ids = (1..20).map { "$it" }.toList()
        val summery = (1..20).associate { index ->

            val category = when {
                index % 3 == 0 -> horse
                index % 3 == 1 -> harness
                else -> grayHound
            }

            index.toString() to RaceSummaryDto(
                race_id = "$index",
                race_number = "$index",
                advertised_start = AdvertisedStartDto(currentTimeToSeconds() + index * SECOND_IN_MILL_SECOND),
                category_id = category,
                meeting_name = "name $index"
            )
        }

        return Response.success(
            ResponseDto(
                message = "success",
                status = 200,
                data = NextToGoDto(ids, summery)
            )
        )
    }
}