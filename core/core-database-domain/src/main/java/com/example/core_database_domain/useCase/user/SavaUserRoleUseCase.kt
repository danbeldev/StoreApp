package com.example.core_database_domain.useCase.user

import com.example.core_database_domain.repository.UserRepository
import com.example.core_model.data.enums.user.UserRole
import javax.inject.Inject

class SavaUserRoleUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(userRole: UserRole){
        userRepository.savaUserRole(userRole)
    }
}