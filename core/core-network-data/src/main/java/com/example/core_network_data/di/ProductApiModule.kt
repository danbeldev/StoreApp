package com.example.core_network_data.di

import com.example.core_network_data.api.ProductApi
import com.example.core_network_data.repository.ProductRepositoryImpl
import com.example.core_network_domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [ProductApiBinds::class])
class ProductApiModule {

    @[Provides Singleton]
    fun providerProductApi(
        retrofit: Retrofit
    ): ProductApi = retrofit.create(ProductApi::class.java)

}

@Module
interface ProductApiBinds {

    @Binds
    fun providerProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

}