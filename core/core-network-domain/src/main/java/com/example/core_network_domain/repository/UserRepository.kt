package com.example.core_network_domain.repository

import com.example.core_model.data.api.user.*
import retrofit2.Response

interface UserRepository {

    suspend fun authorization(authorization: Authorization): Response<AuthorizationResponse>

    suspend fun registration(registration:Registration):Response<RegistrationResult?>

    suspend fun getUser():Response<User>
}