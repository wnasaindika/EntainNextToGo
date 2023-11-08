package com.entain.next.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class EntainApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var entainApi: EntainApi

    @Before
    fun setUp() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        mockWebServer = MockWebServer()
        entainApi = Retrofit
            .Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
            .build().create(EntainApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test entain api returning a list of races for success response`() {
        mockWebServer.enqueue(mockResponse(successResponse))
        val result = runBlocking { entainApi.getNextToGoData("nextraces", 1) }
        mockWebServer.takeRequest()
        assertTrue(result.isSuccessful)
        assertEquals(1, result.body()?.data?.race_summaries?.count())
        assertEquals(1, result.body()?.data?.next_to_go_ids?.count())
    }

    @Test
    fun `test entain api returning a null result of races for malform request`() {
        mockWebServer.enqueue(mockResponse(errorResponse))
        val result = runBlocking { entainApi.getNextToGoData("nextracescc", 1) }
        mockWebServer.takeRequest()
        assertTrue(result.isSuccessful)
        assertEquals(null, result.body()?.data)
    }

    @Test
    fun `test entain api returning a null for malform response `() {
        mockWebServer.enqueue(mockResponse(malformResponse))
        val result = try {
            runBlocking { entainApi.getNextToGoData("nextraces", 1) }
        } catch (e: Exception) {
            null
        }
        mockWebServer.takeRequest()

        assertEquals(null, result?.body())
    }


    private fun mockResponse(response: String): MockResponse {
        val mockResponse = MockResponse()
        mockResponse.setBody(response)
        return mockResponse
    }
}