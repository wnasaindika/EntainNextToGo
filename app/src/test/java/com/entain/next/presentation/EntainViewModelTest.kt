package com.entain.next.presentation

import com.entain.next.domain.usecase.NextToGoRacing
import com.entain.next.domain.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class EntainViewModelTest {

    private lateinit var vm: EntainViewModel
    private val repo: NextToGoRacing = mockk()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        vm = EntainViewModel(repo)
        coEvery { repo.invoke(any()) } returns flow { emit(Resource.Loading()) }
        coEvery { repo.removeExpiredEventFromCache(any()) } returns Unit
    }

    @After
    fun tearDown() {
        Dispatchers.shutdown()
    }

    @Test
    fun `check employees view model exist`() {
        Assert.assertTrue(vm != null)
    }

    @Test
    fun `verify use case invoke and removed expired event called when view model init`() {
        dispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 1) {
            repo.invoke(any())
        }
        coVerify(exactly = 1) {
            repo.removeExpiredEventFromCache(any())
        }
    }
}