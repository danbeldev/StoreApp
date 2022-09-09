package com.example.core_network_data.api

import com.example.core_model.data.api.user.history.History
import com.example.core_model.data.api.user.history.HistoryCreate
import com.example.core_model.data.api.user.history.HistoryType
import com.example.core_model.data.enums.user.UserRole.BaseUser
import com.example.core_network_data.common.ConstantsUrl.USER_HISTORY_URL
import retrofit2.Response
import retrofit2.http.*

interface HistoryApi {

    /**
     * Authorization role [BaseUser]
     * Get user history
     * */
    @GET(USER_HISTORY_URL)
    suspend fun getHistory(
        @Query("historyType") type:HistoryType?
    ):Response<History>

    /**
     * Authorization role [BaseUser]
     * Add user history
     * */
    @POST(USER_HISTORY_URL)
    suspend fun postHistory(@Body body:HistoryCreate)

    /**
     * Authorization role [BaseUser]
     * Delete user history
     * @param id history id
     * */
    @DELETE("$USER_HISTORY_URL/{id}")
    suspend fun deleteHistory(
        @Path("id") id:Int
    )
}