package com.entain.next.domain.usecase

import app.cash.turbine.test
import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.repository.NextToGoRepository
import com.entain.next.domain.util.Resource
import com.entain.next.presentation.data.RaceOrder
import com.entain.next.util.currentTimeToSeconds
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NextToGoRacingTest {

    private lateinit var nextToGoRacing: NextToGoRacing
    private lateinit var fakeRepository: NextToGoRepository

    @Before
    fun setUp() {
        fakeRepository = mockk()
        nextToGoRacing = NextToGoRacing(fakeRepository)
    }

    @Test
    fun `test max limit 5 racing events when user filter by all`() = runTest {
        coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
            Resource.Success(
                fakeData()
            )
        )
        nextToGoRacing.invoke(RaceOrder.ALL).test {
            val item1 = awaitItem()
            assertEquals(5, item1.data?.count())
            assertEquals("grayhound 1", item1.data?.get(0)?.meetingName)
            assertEquals("horse 1", item1.data?.get(1)?.meetingName)
            assertEquals("harness 1", item1.data?.get(2)?.meetingName)
            awaitComplete()
        }
    }

    @Test
    fun `test harness racing events when user filter by harness`() = runTest {
        coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
            Resource.Success(
                fakeData()
            )
        )
        nextToGoRacing.invoke(RaceOrder.Harness).test {
            val item1 = awaitItem()
            assertEquals(4, item1.data?.count())
            awaitComplete()
        }
    }

    @Test
    fun `test horse racing events when user filter by horse`() = runTest {
        coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
            Resource.Success(
                fakeData()
            )
        )
        nextToGoRacing.invoke(RaceOrder.Horse).test {
            val item1 = awaitItem()
            assertEquals(1, item1.data?.count())
            awaitComplete()
        }
    }

    @Test
    fun `test gray hound racing events when user filter by gray hound`() = runTest {
        coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
            Resource.Success(
                fakeData()
            )
        )
        nextToGoRacing.invoke(RaceOrder.GrayHound).test {
            val item1 = awaitItem()
            assertEquals(1, item1.data?.count())
            awaitComplete()
        }
    }

    @Test
    fun `test gray hound and horse racing events when user filter by gray hound and horse`() =
        runTest {
            coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
                Resource.Success(
                    fakeData()
                )
            )
            nextToGoRacing.invoke(RaceOrder.HorseAndGrayHound).test {
                val item1 = awaitItem()
                assertEquals(2, item1.data?.count())
                awaitComplete()
            }
        }

    @Test
    fun `test cache cleared when refresh called`() = runTest {
        coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
            Resource.Success(
                fakeData()
            )
        )
        coEvery { fakeRepository.clearLocalCache() } returns Unit
        nextToGoRacing.refresh()
        coVerify(exactly = 1) {
            fakeRepository.clearLocalCache()
        }
    }

    @Test
    fun `test delete expired event when remove expired event from cache`() = runTest {
        coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
            Resource.Success(
                fakeData()
            )
        )
        coEvery { fakeRepository.deleteExpiredEvent(any()) } returns Unit
        nextToGoRacing.removeExpiredEventFromCache(fakeData()[0])
        coVerify(exactly = 1) {
            fakeRepository.deleteExpiredEvent(any())
        }
    }


    private fun fakeData() = listOf(
        NextToGo(
            raceId = "harness",
            adCategory = Categories.Harness,
            raceNumber = "1",
            meetingName = "harness 1",
            adStartTimeInSeconds = currentTimeToSeconds() + 65L
        ),
        NextToGo(
            raceId = "horse",
            adCategory = Categories.Horse,
            raceNumber = "1",
            meetingName = "horse 1",
            adStartTimeInSeconds = currentTimeToSeconds() + 60L
        ),
        NextToGo(
            raceId = "grayhound",
            adCategory = Categories.GrayHound,
            raceNumber = "1",
            meetingName = "grayhound 1",
            adStartTimeInSeconds = currentTimeToSeconds() + 55L
        ),
        NextToGo(
            raceId = "harness 2",
            adCategory = Categories.Harness,
            raceNumber = "2",
            meetingName = "tes 2",
            adStartTimeInSeconds = currentTimeToSeconds() + 165L
        ),
        NextToGo(
            raceId = "harness 3",
            adCategory = Categories.Harness,
            raceNumber = "1",
            meetingName = "tes",
            adStartTimeInSeconds = currentTimeToSeconds() + 265L
        ),
        NextToGo(
            raceId = "harness 3",
            adCategory = Categories.Harness,
            raceNumber = "1",
            meetingName = "tes",
            adStartTimeInSeconds = currentTimeToSeconds() + 365L
        )
    )
}