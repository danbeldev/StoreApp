package com.example.core_database_domain.useCase.user

import com.example.core_database_domain.repository.UserRepository
import com.example.core_model.data.database.user.UserLogin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke():Flow<UserLogin>{
        return userRepository.getUserLogin()
    }
}