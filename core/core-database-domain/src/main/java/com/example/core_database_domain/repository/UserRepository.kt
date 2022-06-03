package com.example.core_database_domain.repository

import com.example.core_model.data.database.user.UserLogin
import com.example.core_model.data.enums.user.UserRole
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveStatusRegistration(userRegistration:Boolean)

    suspend fun savaUserRole(userRole: UserRole?)

    fun getUserRole(): Flow<UserRole>

    fun getStatusRegistration():Flow<Boolean>

    suspend fun saveUserLogin(userLogin: UserLogin)

    fun getUserLogin():Flow<UserLogin>

    fun getUserToken():Flow<String>

    suspend fun saveUserToken(token: String)
}