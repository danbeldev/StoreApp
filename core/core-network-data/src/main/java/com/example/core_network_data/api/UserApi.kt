package com.example.core_network_data.api

import com.example.core_model.data.api.user.*
import com.example.core_model.data.enums.user.UserRole.BaseUser
import com.example.core_network_data.common.ConstantsUrl.AUTHORIZATION
import com.example.core_network_data.common.ConstantsUrl.GET_USER
import com.example.core_network_data.common.ConstantsUrl.REGISTRATION
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    /**
     * Authorization not required
     * @param authorization email and password
     * */
    @POST(AUTHORIZATION)
    suspend fun authorization(
        @Body authorization: Authorization
    ): Response<AuthorizationResponse>

    /**
     * Authorization not required
     * @param registration user info for registration
     * */
    @POST(REGISTRATION)
    suspend fun registration(
        @Body registration: Registration
    ):Response<RegistrationResult?>

    /**
     * Authorization role [BaseUser]
     * Get user info
     * @param token JWT Authorization Token
     * */
    @GET(GET_USER)
    suspend fun getUser(
        @Header("Authorization") token:String? = null
    ):Response<User>
}