package com.example.core_database_data.repositoryImpl

import com.example.core_database_data.dataSource.UserDataSource
import com.example.core_database_domain.repository.UserRepository
import com.example.core_model.data.database.user.UserLogin
import com.example.core_model.data.enums.user.UserRole
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource
):UserRepository {

    // Safe user status registration
    override suspend fun saveStatusRegistration(userRegistration: Boolean) {
        dataSource.saveStatusRegistration(userRegistration)
    }

    /**
     * save user role [UserRole]
     * */
    override suspend fun savaUserRole(userRole: UserRole?) {
        dataSource.savaUserRole(userRole)
    }

    /**
     * get user tole [UserRole]
     * */
    override fun getUserRole(): Flow<UserRole> = dataSource.getUserRole()

    // Get status registration
    override fun getStatusRegistration(): Flow<Boolean> = dataSource.getStatusRegistration()

    //save user email and password
    override suspend fun saveUserLogin(userLogin: UserLogin) {
        dataSource.saveUserLogin(userLogin)
    }

    //get user email and password
    override fun getUserLogin(): Flow<UserLogin> = dataSource.getUserLogin()

    // get user JWT Authorization Token
    override fun getUserToken(): String? = dataSource.getUserToken()

    // save user JWT Authorization Token
    override suspend fun saveUserToken(token: String) {
        dataSource.saveUserToken(token)
    }
}