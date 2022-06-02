package com.example.core_database_domain.useCase.user

import com.example.core_database_domain.repository.UserRepository
import com.example.core_model.data.database.user.UserLogin
import javax.inject.Inject

class SaveUserLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userLogin: UserLogin){
        userRepository.saveUserLogin(userLogin)
    }
}