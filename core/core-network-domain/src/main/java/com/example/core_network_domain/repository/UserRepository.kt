package com.example.core_network_domain.repository

import com.example.core_model.data.api.user.Authorization
import com.example.core_model.data.api.user.AuthorizationResponse
import com.example.core_model.data.api.user.Registration
import com.example.core_model.data.api.user.User
import retrofit2.Response

interface UserRepository {

    suspend fun authorization(authorization: Authorization): Response<AuthorizationResponse>

    suspend fun registration(registration:Registration):Response<Unit?>

    suspend fun getUser():Response<User>
}