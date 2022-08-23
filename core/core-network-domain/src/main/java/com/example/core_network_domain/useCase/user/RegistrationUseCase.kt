package com.example.core_network_domain.useCase.user

import com.example.core_model.data.api.user.Registration
import com.example.core_model.data.api.user.RegistrationResult
import com.example.core_network_domain.responseApi.BaseApiResponse
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val userRepository: UserRepository
):BaseApiResponse() {

    operator fun invoke(registration: Registration):Flow<Result<RegistrationResult?>> = flow {
        emit( safeApiCall { userRepository.registration(registration) } )
    }
}