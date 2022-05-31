package com.example.storeapp.di.modules

import android.content.Context
import com.example.core_database_data.repositoryImpl.UserRepositoryImpl
import com.example.core_database_domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @[Provides Singleton]
    fun providerUserRepository(
        context: Context
    ):UserRepository = UserRepositoryImpl(
        context = context
    )

}