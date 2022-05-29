package com.example.core_network_data.api

import com.example.core_network_data.common.ConstantsUrl.GET_EVENT
import retrofit2.http.GET
import retrofit2.http.Query

interface EventApi {

    @GET(GET_EVENT)
    suspend fun getEvent(
        @Query("pageSize") pageSize:Int = 20,
        @Query("pageNumber") pageNumber:Int
    )
}