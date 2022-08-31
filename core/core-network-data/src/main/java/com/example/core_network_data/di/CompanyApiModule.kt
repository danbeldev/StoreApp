package com.example.core_network_data.di

import com.example.core_network_data.api.CompanyApi
import com.example.core_network_data.repository.CompanyRepositoryImpl
import com.example.core_network_domain.repository.CompanyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [CompanyApiBinds::class])
class CompanyApiModule {

    @[Provides Singleton]
    fun providerCompanyApi(
        retrofit: Retrofit
    ): CompanyApi = retrofit.create(CompanyApi::class.java)

}

@Module
interface CompanyApiBinds {

    @Binds
    fun providerCompanyRepository(
        companyRepositoryImpl: CompanyRepositoryImpl
    ): CompanyRepository

}