package com.example.storeapp.di.modules

import com.example.core_network_data.api.CompanyApi
import com.example.core_network_data.api.ProductApi
import com.example.core_network_data.api.UserApi
import com.example.core_network_data.api.UserCompanyApi
import com.example.core_network_data.repositoryImpl.CompanyRepositoryImpl
import com.example.core_network_data.repositoryImpl.ProductRepositoryImpl
import com.example.core_network_data.repositoryImpl.UserCompanyRepositoryImpl
import com.example.core_network_data.repositoryImpl.UserRepositoryImpl
import com.example.core_network_domain.repository.CompanyRepository
import com.example.core_network_domain.repository.ProductRepository
import com.example.core_network_domain.repository.UserCompanyRepository
import com.example.core_network_domain.repository.UserRepository
import com.example.storeapp.common.Constants.BASE_URL
import com.example.storeapp.di.modules.annotations.UserToken
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @[Provides Singleton]
    fun providerUserCompanyRepository(
        userCompanyApi: UserCompanyApi
    ):UserCompanyRepository = UserCompanyRepositoryImpl(
        userCompanyApi = userCompanyApi
    )

    @[Provides Singleton]
    fun providerUserCompanyApi(
        retrofit: Retrofit
    ):UserCompanyApi = retrofit.create(UserCompanyApi::class.java)

    @[Provides Singleton]
    fun providerUserRepository(
        userApi: UserApi
    ):UserRepository = UserRepositoryImpl(
        userApi = userApi
    )

    @[Provides Singleton]
    fun providerUserApi(
        retrofit: Retrofit
    ):UserApi = retrofit.create(UserApi::class.java)

    @[Provides Singleton]
    fun providerCompanyRepository(
        companyApi: CompanyApi
    ):CompanyRepository = CompanyRepositoryImpl(
        companyApi = companyApi
    )

    @[Provides Singleton]
    fun providerCompanyApi(
        retrofit: Retrofit
    ):CompanyApi = retrofit.create(CompanyApi::class.java)

    @[Provides Singleton]
    fun providerProductRepository(
        productApi: ProductApi
    ):ProductRepository = ProductRepositoryImpl(
        productApi = productApi
    )

    @[Provides Singleton]
    fun providerProductApi(
        retrofit: Retrofit
    ):ProductApi = retrofit.create(ProductApi::class.java)

    @ExperimentalSerializationApi
    @[Provides Singleton]
    fun providerRetrofit(
        contentType: MediaType,
        json: Json,
        okHttpClient: OkHttpClient
    ):Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(okHttpClient)
        .build()

    @[Singleton Provides]
    fun providerContentType(): MediaType = "application/json".toMediaTypeOrNull()!!

    @ExperimentalSerializationApi
    @[Singleton Provides]
    fun providerJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @[Singleton Provides]
    fun providerOkHttpClient(
        token:String
    ):OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            it.proceed(request = request)
        }
        .build()
}