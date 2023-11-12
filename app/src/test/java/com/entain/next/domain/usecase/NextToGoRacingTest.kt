package com.entain.next.domain.usecase

import app.cash.turbine.test
import com.entain.next.data.dto.AdvertisedStartDto
import com.entain.next.data.dto.NextToGoDto
import com.entain.next.data.dto.RaceSummaryDto
import com.entain.next.data.dto.ResponseDto
import com.entain.next.data.mapper.toNextToGo
import com.entain.next.data.mapper.toNextToGoEntity
import com.entain.next.data.util.grayHound
import com.entain.next.data.util.harness
import com.entain.next.data.util.horse
import com.entain.next.domain.model.Categories
import com.entain.next.domain.repository.NextToGoRepository
import com.entain.next.domain.util.Resource
import com.entain.next.presentation.data.RaceOrder
import com.entain.next.util.currentTimeToSeconds
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NextToGoRacingTest {

    private lateinit var nextToGoRacingImpl: NextToGoRacing
    private lateinit var fakeRepository: NextToGoRepository

    @Before
    fun setUp() {
        fakeRepository = mockk()
        nextToGoRacingImpl = NextToGoRacing(fakeRepository)
        coEvery { fakeRepository.fetchNextToGoRacing() } returns fakeSuccessResponse()
        coEvery { fakeRepository.getNextToGo() } returns fakeData().map { it.toNextToGoEntity() }
    }

    @Test
    fun `test max limit is five for racing events when user filter by any racing event`() =
        runTest {

            nextToGoRacingImpl.invoke(RaceOrder.ALL).test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assert(item1 is Resource.Loading)
                assert(item2 is Resource.Loading)
                assertEquals(5, item3.data?.count())
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `test returning only harness racing events when user filter by harness`() =
        runTest {
            nextToGoRacingImpl.invoke(RaceOrder.Harness).test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assert(item1 is Resource.Loading)
                assert(item2 is Resource.Loading)
                assertEquals(5, item3.data?.count())
                assertEquals(true, item3.data?.all { it.adCategory == Categories.Harness })
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `test returning only horse racing events when user filter by horse`() = runTest {
        nextToGoRacingImpl.invoke(RaceOrder.Horse).test {
            val item1 = awaitItem()
            val item2 = awaitItem()
            val item3 = awaitItem()
            assert(item1 is Resource.Loading)
            assert(item2 is Resource.Loading)
            assertEquals(5, item3.data?.count())
            assertEquals(true, item3.data?.all { it.adCategory == Categories.Horse })
            awaitComplete()
        }
    }

    @Test
    fun `test returning only gray hound racing events when user filter by gray hound`() = runTest {

        nextToGoRacingImpl.invoke(RaceOrder.GrayHound).test {
            val item1 = awaitItem()
            val item2 = awaitItem()
            val item3 = awaitItem()
            assert(item1 is Resource.Loading)
            assert(item2 is Resource.Loading)
            assertEquals(5, item3.data?.count())
            assertEquals(true, item3.data?.all { it.adCategory == Categories.GrayHound })
            awaitComplete()
        }
    }

    @Test
    fun `test returning only gray hound and horse racing events when user filter by gray hound and horse`() =
        runTest {
            nextToGoRacingImpl.invoke(RaceOrder.HorseAndGrayHound).test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()
                assert(item1 is Resource.Loading)
                assert(item2 is Resource.Loading)
                assertEquals(5, item3.data?.count())
                assertEquals(false, item3.data?.any { it.adCategory == Categories.Harness })
                awaitComplete()
            }
        }

    @Test
    fun `test cache cleared when refresh called`() = runTest {
        coEvery { fakeRepository.clearLocalCache() } returns Unit
        nextToGoRacingImpl.refresh()
        coVerify(exactly = 1) {
            fakeRepository.clearLocalCache()
        }
    }

    @Test
    fun `test delete expired event when user remove expired event from cache`() = runTest {

        coEvery { fakeRepository.deleteExpiredCachedEvent(any()) } returns Unit
        nextToGoRacingImpl.removeExpiredEventFromCache(fakeData().map { it.toNextToGoEntity() }
            .map { it.toNextToGo() }.first())
        coVerify(exactly = 1) {
            fakeRepository.deleteExpiredCachedEvent(any())
        }
    }

    private fun fakeSuccessResponse() = Response.success(
        ResponseDto(
            message = "success",
            status = 200,
            data = NextToGoDto(
                next_to_go_ids = fakeData().map { it.race_id }.toList(),
                race_summaries = fakeData().associateBy { it.race_id }
            )
        )
    )


    private fun fakeData(reduceSizeEnable: Boolean = false): List<RaceSummaryDto> {
        val harness = (0..6).map {
            RaceSummaryDto(
                race_id = "harness $it",
                category_id = harness,
                race_number = "id$it",
                meeting_name = "harness $it",
                advertised_start = AdvertisedStartDto(currentTimeToSeconds() + 65L + it * 2)
            )
        }

        val horse = (0..6).map {
            RaceSummaryDto(
                race_id = "horse $it",
                category_id = horse,
                race_number = "id$it",
                meeting_name = "horse $it",
                advertised_start = AdvertisedStartDto(currentTimeToSeconds() + 66L + it * 3)
            )
        }

        val grayHound = (0..6).map {
            RaceSummaryDto(
                race_id = "horse $it",
                category_id = grayHound,
                race_number = "id$it",
                meeting_name = "horse $it",
                advertised_start = AdvertisedStartDto(currentTimeToSeconds() + 67L + it * 4)
            )
        }

        return horse + grayHound + if (reduceSizeEnable) harness.take(1) else harness
    }
}