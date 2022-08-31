package com.example.core_database_data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.core_common.annotations.di.UserToken
import com.example.core_database_data.dataSource.UserDataSource
import com.example.core_database_data.repositoryImpl.UserRepositoryImpl
import com.example.core_database_domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [UserDatabaseBinds::class])
class UserDatabaseModule {

    @[Provides Singleton]
    fun providerUserDataSource(
        context: Context,
        @UserToken sharedPreferences: SharedPreferences
    ):UserDataSource = UserDataSource(
        context = context,
        sharedPreferences = sharedPreferences
    )

    // Create Shared pref JWT Token Authorization
    @[Provides Singleton UserToken]
    fun providerSharedPref(
        context: Context
    ): SharedPreferences = context.getSharedPreferences(
        UserDataSource.USER_TOKEN,
        Context.MODE_PRIVATE
    )

    // Authorization JWT Token
    @[Provides Singleton UserToken]
    fun providerAuthorizationToken(
        @UserToken sharedPreferences: SharedPreferences
    ):String = sharedPreferences.getString(
        UserDataSource.USER_TOKEN,
        ""
    ) ?: ""

}

@Module
interface UserDatabaseBinds {

    @Binds
    fun providerUserDatabaseRepository(
        repository: UserRepositoryImpl
    ):UserRepository

}