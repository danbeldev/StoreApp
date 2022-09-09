package com.example.core_network_data.di

import com.example.core_network_data.api.HistoryApi
import com.example.core_network_data.repository.HistoryRepositoryImpl
import com.example.core_network_domain.repository.HistoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [HistoryApiBinds::class])
class HistoryApiModule {

    @[Provides Singleton]
    fun providerHistoryApi(
        retrofit: Retrofit
    ):HistoryApi = retrofit.create(HistoryApi::class.java)
}

@Module
interface HistoryApiBinds {

    @Binds
    fun providerHistoryRepository(
        impl:HistoryRepositoryImpl
    ):HistoryRepository
}