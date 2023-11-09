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
    fun `test max limit is five for racing events when user filter by any racing event`() =
        runTest {
            coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
                Resource.Success(
                    fakeData()
                )
            )
            nextToGoRacing.invoke(RaceOrder.ALL).test {
                val item1 = awaitItem()
                assertEquals(5, item1.data?.count())
                awaitComplete()
            }
        }

    @Test
    fun `test returning only harness racing events when user filter by harness`() = runTest {
        coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
            Resource.Success(
                fakeData()
            )
        )
        nextToGoRacing.invoke(RaceOrder.Harness).test {
            val item1 = awaitItem()
            assertEquals(5, item1.data?.count())
            assertEquals(true, item1.data?.all { it.adCategory == Categories.Harness })
            awaitComplete()
        }
    }

    @Test
    fun `test returning only horse racing events when user filter by horse`() = runTest {
        coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
            Resource.Success(
                fakeData()
            )
        )
        nextToGoRacing.invoke(RaceOrder.Horse).test {
            val item1 = awaitItem()
            assertEquals(5, item1.data?.count())
            assertEquals(true, item1.data?.all { it.adCategory == Categories.Horse })
            awaitComplete()
        }
    }

    @Test
    fun `test returning only gray hound racing events when user filter by gray hound`() = runTest {
        coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
            Resource.Success(
                fakeData()
            )
        )
        nextToGoRacing.invoke(RaceOrder.GrayHound).test {
            val item1 = awaitItem()
            assertEquals(5, item1.data?.count())
            assertEquals(true, item1.data?.all { it.adCategory == Categories.GrayHound })
            awaitComplete()
        }
    }

    @Test
    fun `test returning only gray hound and horse racing events when user filter by gray hound and horse`() =
        runTest {
            coEvery { fakeRepository.fetchNextToGoRacing() } returns flowOf(
                Resource.Success(
                    fakeData()
                )
            )
            nextToGoRacing.invoke(RaceOrder.HorseAndGrayHound).test {
                val item1 = awaitItem()
                assertEquals(5, item1.data?.count())
                assertEquals(false, item1.data?.any { it.adCategory == Categories.Harness })
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
    fun `test delete expired event when user remove expired event from cache`() = runTest {
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


    private fun fakeData(reduceSizeEnable: Boolean = false): List<NextToGo> {
        val harness = (0..6).map {
            NextToGo(
                raceId = "harness $it",
                adCategory = Categories.Harness,
                raceNumber = "id$it",
                meetingName = "harness $it",
                adStartTimeInSeconds = currentTimeToSeconds() + 65L + it * 2
            )
        }

        val horse = (0..6).map {
            NextToGo(
                raceId = "horse $it",
                adCategory = Categories.Horse,
                raceNumber = "id$it",
                meetingName = "horse $it",
                adStartTimeInSeconds = currentTimeToSeconds() + 66L + it * 3
            )
        }

        val grayHound = (0..6).map {
            NextToGo(
                raceId = "horse $it",
                adCategory = Categories.GrayHound,
                raceNumber = "id$it",
                meetingName = "horse $it",
                adStartTimeInSeconds = currentTimeToSeconds() + 67L + it * 4
            )
        }


        return horse + grayHound + if (reduceSizeEnable) harness.take(1) else harness
    }
}