package com.example.core_database_domain.useCase.user

import com.example.core_database_domain.repository.UserRepository
import javax.inject.Inject

class SaveUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(token:String){
        userRepository.saveUserToken(token)
    }
}