package com.example.core_network_data.api

import com.example.core_model.data.api.event.Event
import com.example.core_network_data.common.ConstantsUrl.GET_EVENT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EventApi {

    /**
     * Get Events Company
     * Authorization not required
     * @param pageSize A page number within the paginated result set
     * @param pageNumber A page number within the paginated result set
     * */
    @GET(GET_EVENT)
    suspend fun getEvent(
        @Query("pageSize") pageSize:Int = 20,
        @Query("pageNumber") pageNumber:Int
    ):Response<Event>
}