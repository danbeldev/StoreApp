package com.example.core_network_data.repositoryImpl

import com.example.core_model.data.api.user.*
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

    override suspend fun registration(registration: Registration): Response<RegistrationResult?> {
        return userApi.registration(registration)
    }

    override suspend fun getUser(): Response<User> {
        return userApi.getUser()
    }
}