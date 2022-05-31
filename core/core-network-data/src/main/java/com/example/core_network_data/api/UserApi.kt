package com.example.core_network_data.api

import com.example.core_model.data.api.user.Authorization
import com.example.core_model.data.api.user.AuthorizationResponse
import com.example.core_network_data.common.ConstantsUrl.AUTHORIZATION
import retrofit2.Response
import retrofit2.http.POST

interface UserApi {

    @POST(AUTHORIZATION)
    suspend fun authorization(
        authorization: Authorization
    ): Response<AuthorizationResponse>

}