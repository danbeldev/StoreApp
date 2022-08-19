package com.example.storeapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.core_database_data.repositoryImpl.SettingsRepositoryImpl
import com.example.core_database_data.repositoryImpl.UserRepositoryImpl
import com.example.core_database_domain.repository.SettingsRepository
import com.example.core_database_domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @[Provides Singleton]
    fun providerUserRepository(
        context: Context,
        sharedPreferences: SharedPreferences
    ):UserRepository = UserRepositoryImpl(
        context = context,
        sharedPreferences = sharedPreferences
    )

    @[Provides Singleton]
    fun providerSettingsRepository(
        context: Context
    ): SettingsRepository = SettingsRepositoryImpl(
        context = context
    )

    // Create Shared pref JWT Token Authorization
    @[Provides Singleton]
    fun providerSharedPref(
        context:Context
    ): SharedPreferences = context.getSharedPreferences(
        UserRepositoryImpl.USER_TOKEN,
        Context.MODE_PRIVATE
    )
}