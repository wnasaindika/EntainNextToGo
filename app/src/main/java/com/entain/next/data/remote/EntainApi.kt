package com.entain.next.data.remote

import com.entain.next.data.dto.ResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EntainApi {

    @GET("rest/v1/racing/")
    suspend fun getNextToGoData(
        @Query("method") method: String,
        @Query("count") count: Int
    ): Response<ResponseDto>

    companion object{
        const val URL = "https://api.neds.com.au/"
        const val REQUEST_METHOD = "nextraces"
        const val NUMBER_OF_RACES = 500
    }
}