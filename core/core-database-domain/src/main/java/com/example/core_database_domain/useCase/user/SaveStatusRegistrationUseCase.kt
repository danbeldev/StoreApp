package com.example.core_database_domain.useCase.user

import com.example.core_database_domain.repository.UserRepository
import javax.inject.Inject

class SaveStatusRegistrationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(statusRegistration:Boolean){
        userRepository.saveStatusRegistration(statusRegistration)
    }
}