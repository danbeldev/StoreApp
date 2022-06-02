package com.example.core_database_domain.useCase.user

import com.example.core_database_domain.repository.UserRepository
import com.example.core_model.data.enums.user.UserRole
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRoleUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke():Flow<UserRole>{
        return userRepository.getUserRole()
    }
}