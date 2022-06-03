package com.example.core_database_data.repositoryImpl

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core_common.extension.decodeFromString
import com.example.core_common.extension.encodeToString
import com.example.core_database_domain.repository.UserRepository
import com.example.core_model.data.database.user.UserLogin
import com.example.core_model.data.enums.user.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val context: Context
):UserRepository {

    override suspend fun saveStatusRegistration(userRegistration: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[USER_STATUS_REGISTRATION] = userRegistration
        }
    }

    override suspend fun savaUserRole(userRole: UserRole?) {
        context.userDataStore.edit { preferences ->
            preferences[USER_ROLE] = userRole?.name ?: UserRole.BaseUser.name
        }
    }

    override fun getUserRole(): Flow<UserRole> {
        return context.userDataStore.data
            .map { preferences ->
                enumValueOf(
                    preferences[USER_ROLE] ?: UserRole.BaseUser.name
                )
            }
    }

    override fun getStatusRegistration(): Flow<Boolean> {
        return context.userDataStore.data
            .map { preferences ->
                preferences[USER_STATUS_REGISTRATION] ?: false
            }
    }

    override suspend fun saveUserLogin(userLogin: UserLogin) {
        context.userDataStore.edit { preferences ->
            preferences[USER_LOGIN] = userLogin.encodeToString()
        }
    }

    override fun getUserLogin(): Flow<UserLogin> {
        return context.userDataStore.data
            .map { preferences ->
                val data = preferences[USER_LOGIN]?.decodeFromString<UserLogin>()
                data ?: UserLogin("","")
            }
    }

    override fun getUserToken(): Flow<String> {
        return context.userDataStore.data
            .map { preferences ->
                preferences[USER_TOKEN] ?: ""
            }
    }

    override suspend fun saveUserToken(token: String) {
        context.userDataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    companion object{
        private val Context.userDataStore by preferencesDataStore(name = "user_data_store")
        val USER_STATUS_REGISTRATION = booleanPreferencesKey("user_status_registration_key")
        val USER_ROLE = stringPreferencesKey("user_role_key")
        val USER_LOGIN = stringPreferencesKey("user_login_key")
        val USER_TOKEN = stringPreferencesKey("user_token_key")
    }
}