package com.entain.next.domain.repository

import com.entain.next.data.dto.AdvertisedStartDto
import com.entain.next.data.dto.NextToGoDto
import com.entain.next.data.dto.RaceSummaryDto
import com.entain.next.data.dto.ResponseDto
import com.entain.next.data.local.LocalRaceSummery
import com.entain.next.data.mapper.toNextToGo
import com.entain.next.domain.model.Categories
import com.entain.next.util.currentTimeToSeconds
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class NextToGoRepositoryTest {

    private val fakeNextToGoRepository = FakeNextToGoRepository()


    @Test
    fun `test fetch next to go racing work as expected when fetching success`() = runTest {
        fakeNextToGoRepository.emit(dbList = localData())
        val result = fakeNextToGoRepository.fetchNextToGoRacing()
        assertEquals(null, result)
        fakeNextToGoRepository.emit(dbList = localData(), response = fetchSuccessData())
        val newResult = fakeNextToGoRepository.fetchNextToGoRacing()
        assertTrue(newResult?.isSuccessful == true)
        assertEquals(7, newResult?.body()?.data?.race_summaries?.count())
    }

    @Test
    fun `test fetch next to go racing returns null when fetching errors`() = runTest {
        fakeNextToGoRepository.emit(dbList = localData(), response = fetchErrorData())
        val result = fakeNextToGoRepository.fetchNextToGoRacing()
        assertTrue(result?.isSuccessful == true)
        assertEquals(null, result?.body()?.data)
    }

    @Test
    fun `test clear cache and extract remote data as CachedNextToGo`() = runTest {
        fakeNextToGoRepository.emit(dbList = localDataExpired())
        val cached = fakeNextToGoRepository.getNextToGo()
        assertEquals(1, cached.count())
        assertEquals("expired test", cached.first().meetingName)
        val result = fakeNextToGoRepository.clearCacheAndExtractRemoteData(fetchSuccessData())
        assertEquals(7, result?.count())
        assertEquals("name 0", result?.first()?.meetingName)
    }

    @Test
    fun `test clear cache work as expected`() = runTest {
        fakeNextToGoRepository.emit(dbList = localDataExpired())
        val cached = fakeNextToGoRepository.getNextToGo()
        assertEquals(1, cached.count())
        assertEquals("expired test", cached.first().meetingName)
        fakeNextToGoRepository.clearLocalCache()
        val newCached = fakeNextToGoRepository.getNextToGo()
        assertEquals(0, newCached.count())
    }

    @Test
    fun `test delete expired cached work as expected`() = runTest {
        fakeNextToGoRepository.emit(dbList = localDataExpired() + localData())
        val cached = fakeNextToGoRepository.getNextToGo()
        assertEquals(2, cached.count())
        fakeNextToGoRepository.deleteExpiredCachedEvent(localDataExpired().map { it.toNextToGo() }
            .first())
        val newCached = fakeNextToGoRepository.getNextToGo()
        assertEquals(1, newCached.count())
    }

    @Test
    fun `test delete all expired cache work as expected`() = runTest {
        fakeNextToGoRepository.emit(dbList = localDataExpired() + localDataExpired())
        val cached = fakeNextToGoRepository.getNextToGo()
        assertEquals(2, cached.count())
        fakeNextToGoRepository.deleteExpiredEvents()
        val newCached = fakeNextToGoRepository.getNextToGo()
        assertEquals(0, newCached.count())
    }

    private fun fetchErrorData() = Response.success(
        ResponseDto(
            data = null,
            message = "test",
            status = 200
        )
    )

    private fun fetchSuccessData(): Response<ResponseDto> {
        val ids = (0..6).map { "$it" }
        val raceSummery = (0..6).withIndex().mapIndexed { index, indexedValue ->
            index.toString() to RaceSummaryDto(
                race_id = "$index",
                race_number = "$index",
                advertised_start = AdvertisedStartDto(currentTimeToSeconds() + 3L + index),
                category_id = "harness",
                meeting_name = "name $index"
            )
        }.toMap()

        return Response.success(
            ResponseDto(
                data = NextToGoDto(
                    next_to_go_ids = ids,
                    race_summaries = raceSummery
                ),
                message = "test",
                status = 200
            )
        )
    }

    private fun localDataExpired() = listOf(
        LocalRaceSummery(
            raceId = "2312311 2",
            adCategory = Categories.Harness,
            raceNumber = "1",
            meetingName = "expired test",
            adStartTimeInSeconds = currentTimeToSeconds() - 65L
        )
    )

    private fun localData() = listOf(
        LocalRaceSummery(
            raceId = "test",
            adCategory = Categories.Harness,
            raceNumber = "1",
            meetingName = "tes",
            adStartTimeInSeconds = currentTimeToSeconds() + 65L
        )
    )
}