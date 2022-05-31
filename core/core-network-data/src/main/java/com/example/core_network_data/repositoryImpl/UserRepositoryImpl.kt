package com.example.core_network_data.repositoryImpl

import com.example.core_model.data.api.user.Authorization
import com.example.core_model.data.api.user.AuthorizationResponse
import com.example.core_network_data.api.UserApi
import com.example.core_network_domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserRepository {
    override suspend fun authorization(authorization: Authorization): Response<AuthorizationResponse> {
        return userApi.authorization(authorization)
    }
}