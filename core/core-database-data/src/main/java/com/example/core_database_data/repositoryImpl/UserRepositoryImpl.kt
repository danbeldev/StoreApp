package com.example.core_database_data.repositoryImpl

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core_database_domain.repository.UserRepository
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

    override suspend fun savaUserRole(userRole: UserRole) {
        context.userDataStore.edit { preferences ->
            preferences[USER_ROLE] = userRole.name
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

    companion object{
        private val Context.userDataStore by preferencesDataStore(name = "user_data_store")
        val USER_STATUS_REGISTRATION = booleanPreferencesKey("user_status_registration_key")
        val USER_ROLE = stringPreferencesKey("user_role_key")
    }
}