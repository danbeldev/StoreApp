package com.example.core_database_data.dataSource

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core_common.annotations.di.UserToken
import com.example.core_common.extension.decodeFromString
import com.example.core_common.extension.encodeToString
import com.example.core_model.data.database.user.UserLogin
import com.example.core_model.data.enums.user.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val context: Context,
    @UserToken private val sharedPreferences: SharedPreferences
) {
    // Safe user status registration
    suspend fun saveStatusRegistration(userRegistration: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[USER_STATUS_REGISTRATION] = userRegistration
        }
    }

    /**
     * save user role [UserRole]
     * */
    suspend fun savaUserRole(userRole: UserRole?) {
        context.userDataStore.edit { preferences ->
            preferences[USER_ROLE] = userRole?.name ?: UserRole.BaseUser.name
        }
    }

    /**
     * get user tole [UserRole]
     * */
    fun getUserRole(): Flow<UserRole> {
        return context.userDataStore.data
            .map { preferences ->
                enumValueOf(
                    preferences[USER_ROLE] ?: UserRole.BaseUser.name
                )
            }
    }

    // Get status registration
    fun getStatusRegistration(): Flow<Boolean> {
        return context.userDataStore.data
            .map { preferences ->
                preferences[USER_STATUS_REGISTRATION] ?: false
            }
    }

    //save user email and password
    suspend fun saveUserLogin(userLogin: UserLogin) {
        context.userDataStore.edit { preferences ->
            preferences[USER_LOGIN] = userLogin.encodeToString()
        }
    }

    //get user email and password
    fun getUserLogin(): Flow<UserLogin> {
        return context.userDataStore.data
            .map { preferences ->
                val data = preferences[USER_LOGIN]?.decodeFromString<UserLogin>()
                data ?: UserLogin("","")
            }
    }

    // get user JWT Authorization Token
    fun getUserToken(): String? {
        return sharedPreferences.getString(USER_TOKEN,"")
    }

    // save user JWT Authorization Token
    suspend fun saveUserToken(token: String) {
        sharedPreferences.edit()
            .putString(USER_TOKEN,token)
            .apply()
    }

    companion object{
        // Create user data store
        private val Context.userDataStore by preferencesDataStore(name = "user_data_store")
        val USER_STATUS_REGISTRATION = booleanPreferencesKey("user_status_registration_key")
        /**
         * @see USER_ROLE name enum [UserRole]
         * */
        val USER_ROLE = stringPreferencesKey("user_role_key")
        /**
         *  @see USER_LOGIN json [UserLogin]
         * */
        val USER_LOGIN = stringPreferencesKey("user_login_key")

        // Shared pref
        const val USER_TOKEN = "user_token_key"
    }
}