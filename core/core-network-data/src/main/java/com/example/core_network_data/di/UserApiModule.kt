package com.example.core_network_data.di

import com.example.core_network_data.api.UserApi
import com.example.core_network_data.repository.UserRepositoryImpl
import com.example.core_network_domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [UserApiBinds::class])
class UserApiModule {

    @[Provides Singleton]
    fun providerUserApi(
        retrofit: Retrofit
    ): UserApi = retrofit.create(UserApi::class.java)
}

@Module
interface UserApiBinds {
    @Binds
    fun providerUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}