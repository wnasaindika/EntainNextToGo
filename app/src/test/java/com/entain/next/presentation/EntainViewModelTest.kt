package com.entain.next.presentation

import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.usecase.NextToGoRacing
import com.entain.next.domain.util.Resource
import com.entain.next.presentation.data.RaceEvent
import com.entain.next.presentation.data.UiState
import com.entain.next.presentation.event_mapper.RaceCombinations
import com.entain.next.util.currentTimeToSeconds
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyAll
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EntainViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private lateinit var vm: EntainViewModel
    private val repo: NextToGoRacing = mockk()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        vm = EntainViewModel(repo)
        coEvery { repo.invoke(any()) } returns flow { emit(Resource.Loading()) }
        coEvery { repo.removeExpiredEventFromCache(any()) } returns Unit
        coEvery { repo.checkAndRemoveExpiredEvents() } returns Unit
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `check employees view model exist`() {
        assertTrue(vm != null)
    }

    @Test
    fun `verify use case invoke method called when viewmodel initialize`() = runTest {
        dispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 1) {
            repo.invoke(any())
        }
    }

    @Test
    fun `test loading state when view model initialize and repo invoke is loading`() = runTest {
        dispatcher.scheduler.advanceUntilIdle()
        val result = vm.uiState.first()
        coVerifyAll {
            repo.invoke(any())
            repo.checkAndRemoveExpiredEvents()
        }
        assertTrue(result is UiState.Loading)
    }

    @Test
    fun `test success state when view model initialize and repo invoke is success`() = runTest {
        vm.onRaceEvents(RaceEvent.SelectRace(RaceCombinations.getAllRacing))

        coEvery { repo.invoke(any()) } returns flow {
            emit(
                Resource.Success(
                    listOf(
                        NextToGo(
                            raceId = "1",
                            raceNumber = "2",
                            adStartTimeInSeconds = currentTimeToSeconds(),
                            meetingName = "name",
                            adCategory = Categories.Horse
                        )
                    )
                )
            )
        }
        dispatcher.scheduler.advanceUntilIdle()
        val value = vm.uiState.first()
        coVerifyAll {
            repo.invoke(any())
            repo.checkAndRemoveExpiredEvents()
        }

        assertTrue(value is UiState.Success)

    }


    @Test
    fun `test error state when view model initialize and repo invoke is error`() = runTest {
        vm.onRaceEvents(RaceEvent.SelectRace(RaceCombinations.getAllRacing))
        coEvery { repo.invoke(any()) } returns flow {
            emit(
                Resource.Error(message = "Error")
            )
        }
        dispatcher.scheduler.advanceUntilIdle()
        val value = vm.uiState.first()
        coVerifyAll {
            repo.invoke(any())
            repo.checkAndRemoveExpiredEvents()
        }

        assertTrue(value is UiState.Error)

    }
}