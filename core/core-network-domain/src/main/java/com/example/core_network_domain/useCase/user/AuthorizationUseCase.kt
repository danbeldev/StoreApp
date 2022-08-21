package com.example.core_network_domain.useCase.user

import com.example.core_model.data.api.user.Authorization
import com.example.core_model.data.api.user.AuthorizationResult
import com.example.core_network_domain.apiResponse.BaseApiResponse
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(
    private val userRepository: UserRepository
):BaseApiResponse() {
    operator fun invoke(authorization: Authorization):Flow<Result<AuthorizationResult>> = flow {
        emit( safeApiCall { userRepository.authorization(authorization) } )
    }
}