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
    fun `test the remote api is not called, if the local cached is not expired (after 1 min) then return local cache`() =
        runTest {
            fakeNextToGoRepository.emit(dbList = localData(), response = fetchSuccessData())
            fakeNextToGoRepository.fetchNextToGoRacing().test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assertTrue(item1 is Resource.Loading)
                assertTrue(item2 is Resource.Success)
                assertTrue(item3 is Resource.Loading)
                assertEquals(1, item2.data?.count())
                awaitComplete()
            }
        }

    @Test
    fun `test the remote api called, if the local cached is expired (after 1 min) then remote api update the cache`() =
        runTest {
            fakeNextToGoRepository.emit(dbList = localDataExpired(), response = fetchSuccessData())
            fakeNextToGoRepository.fetchNextToGoRacing().test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assertTrue(item1 is Resource.Loading)
                assertTrue(item2 is Resource.Success)
                assertTrue(item3 is Resource.Loading)
                assertEquals(1, item2.data?.count())
                assertEquals("name", item2.data?.first()?.meetingName)
                awaitComplete()
            }
        }

    @Test
    fun `test the remote api called and receive error and if the local cached is expired (after 1 min) then error returning`() =
        runTest {
            fakeNextToGoRepository.emit(dbList = localDataExpired(), response = fetchErrorData())
            fakeNextToGoRepository.fetchNextToGoRacing().test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assertTrue(item1 is Resource.Loading)
                assertTrue(item2 is Resource.Error)
                assertTrue(item3 is Resource.Loading)
                awaitComplete()
            }
        }

    @Test
    fun `test local cached clear and then call remote api is then new item added to the local cached`() =
        runTest {
            fakeNextToGoRepository.emit(localData(), fetchSuccessData())
            fakeNextToGoRepository.clearLocalCache()
            fakeNextToGoRepository.fetchNextToGoRacing().test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assertEquals(1, item2.data?.count())
                assertEquals("name", item2.data?.first()?.meetingName)
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

    private fun fetchSuccessData() = Response.success(
        ResponseDto(
            data = NextToGoDto(
                next_to_go_ids = listOf("1"), race_summaries = mapOf(
                    Pair(
                        "1", RaceSummaryDto(
                            race_id = "1",
                            race_number = "1",
                            advertised_start = AdvertisedStartDto(currentTimeToSeconds() + 3L),
                            category_id = "harness",
                            meeting_name = "name"
                        )
                    )
                )
            ),
            message = "test",
            status = 200
        )
    )

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