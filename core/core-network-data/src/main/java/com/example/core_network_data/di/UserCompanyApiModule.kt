package com.example.core_network_data.di

import com.example.core_network_data.api.UserCompanyApi
import com.example.core_network_data.repository.UserCompanyRepositoryImpl
import com.example.core_network_domain.repository.UserCompanyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [UserCompanyApiBinds::class])
class UserCompanyApiModule {

    @[Provides Singleton]
    fun providerUserCompanyApi(
        retrofit: Retrofit
    ): UserCompanyApi = retrofit.create(UserCompanyApi::class.java)

}

@Module
interface UserCompanyApiBinds {
    @Binds
    fun providerUserCompanyRepository(
        userCompanyRepositoryImpl: UserCompanyRepositoryImpl
    ): UserCompanyRepository

}
