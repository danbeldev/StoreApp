package com.example.core_network_domain.repository

import com.example.core_model.data.api.user.*
import retrofit2.Response

interface UserRepository {

    /**
     * @param authorization email and password
     * */
    suspend fun authorization(authorization: Authorization): Response<AuthorizationResult>

    /**
     * @param registration user info for registration
     * */
    suspend fun registration(registration:Registration):Response<RegistrationResult?>

    /**
     * Get user info
     * */
    suspend fun getUser():Response<User>
}