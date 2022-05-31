package com.example.core_database_domain.repository

import com.example.core_model.data.enums.user.UserRole
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveStatusRegistration(userRegistration:Boolean)

    suspend fun savaUserRole(userRole: UserRole)

    fun getUserRole(): Flow<UserRole>

    fun getStatusRegistration():Flow<Boolean>
}