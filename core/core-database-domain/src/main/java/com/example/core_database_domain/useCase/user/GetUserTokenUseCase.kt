package com.example.core_database_domain.useCase.user

import com.example.core_database_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke():Flow<String>{
        return userRepository.getUserToken()
    }
}