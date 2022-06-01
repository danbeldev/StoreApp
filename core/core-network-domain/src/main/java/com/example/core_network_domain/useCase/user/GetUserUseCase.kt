package com.example.core_network_domain.useCase.user

import com.example.core_model.data.api.user.User
import com.example.core_network_domain.apiResponse.BaseApiResponse
import com.example.core_network_domain.apiResponse.Result
import com.example.core_network_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
):BaseApiResponse() {

    operator fun invoke():Flow<Result<User>> = flow {
        emit( safeApiCall { userRepository.getUser() } )
    }
}