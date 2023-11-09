package com.entain.next.domain.repository

import app.cash.turbine.test
import com.entain.next.data.dto.AdvertisedStartDto
import com.entain.next.data.dto.NextToGoDto
import com.entain.next.data.dto.RaceSummaryDto
import com.entain.next.data.dto.ResponseDto
import com.entain.next.data.local.NextToGoEntity
import com.entain.next.domain.model.Categories
import com.entain.next.domain.util.Resource
import com.entain.next.util.currentTimeToSeconds

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class NextToGoRepositoryTest {

    private val fakeNextToGoRepository = FakeNextToGoRepository()

    @Test
    fun `test local cache is only work when events are not empty or not less than 5 or no expired items`() =
        runTest {
            fakeNextToGoRepository.emit(dbList = localData(), response = fetchSuccessData())
            fakeNextToGoRepository.fetchNextToGoRacing().test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assertTrue(item1 is Resource.Loading)
                assertTrue(item2 is Resource.Success)
                assertTrue(item3 is Resource.Loading)
                assertEquals(7, item2.data?.count())
                awaitComplete()
            }
        }

    @Test
    fun `test invalidate expired race event and consume data from remote api`() =
        runTest {
            fakeNextToGoRepository.emit(dbList = localDataExpired(), response = fetchSuccessData())
            fakeNextToGoRepository.fetchNextToGoRacing().test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assertTrue(item1 is Resource.Loading)
                assertTrue(item2 is Resource.Success)
                assertTrue(item3 is Resource.Loading)
                assertEquals(7, item2.data?.count())
                assertEquals("name 0", item2.data?.first()?.meetingName)
                awaitComplete()
            }
        }

    @Test
    fun `test the remote api error, if the local cached is expired (after 1 min) then error returning`() =
        runTest {
            fakeNextToGoRepository.emit(dbList = localDataExpired(), response = fetchErrorData())
            fakeNextToGoRepository.fetchNextToGoRacing().test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assertTrue(item1 is Resource.Loading)
                assertTrue(item2 is Resource.Loading)
                assertTrue(item3 is Resource.Error)
                awaitComplete()
            }
        }

    @Test
    fun `test when clearLocalCache clean up race event and then fetch remote data and new items are added to the local cached`() =
        runTest {
            fakeNextToGoRepository.emit(localData(), fetchSuccessData())
            fakeNextToGoRepository.clearLocalCache()
            fakeNextToGoRepository.fetchNextToGoRacing().test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assertEquals(7, item2.data?.count())
                assertEquals("name 0", item2.data?.first()?.meetingName)
                awaitComplete()
            }
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
        NextToGoEntity(
            raceId = "test",
            adCategory = Categories.Harness,
            raceNumber = "1",
            meetingName = "tes",
            adStartTimeInSeconds = currentTimeToSeconds() - 65L
        )
    )

    private fun localData() = listOf(
        NextToGoEntity(
            raceId = "test",
            adCategory = Categories.Harness,
            raceNumber = "1",
            meetingName = "tes",
            adStartTimeInSeconds = currentTimeToSeconds() + 65L
        )
    )
}