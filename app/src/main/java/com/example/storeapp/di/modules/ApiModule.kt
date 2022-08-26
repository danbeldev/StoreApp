package com.example.storeapp.di.modules

import android.content.SharedPreferences
import com.example.core_network_data.api.CompanyApi
import com.example.core_network_data.api.ProductApi
import com.example.core_network_data.api.UserApi
import com.example.core_network_data.api.UserCompanyApi
import com.example.core_network_data.repository.CompanyRepositoryImpl
import com.example.core_network_data.repository.ProductRepositoryImpl
import com.example.core_network_data.repository.UserCompanyRepositoryImpl
import com.example.core_network_data.repository.UserRepositoryImpl
import com.example.core_network_data.retrofit.RetrofitInst
import com.example.core_network_domain.repository.CompanyRepository
import com.example.core_network_domain.repository.ProductRepository
import com.example.core_network_domain.repository.UserCompanyRepository
import com.example.core_network_domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [ApiModuleBinds::class])
class ApiModule {

    @[Provides Singleton]
    fun providerUserCompanyApi(
        retrofit: Retrofit
    ):UserCompanyApi = retrofit.create(UserCompanyApi::class.java)

    @[Provides Singleton]
    fun providerUserApi(
        retrofit: Retrofit
    ):UserApi = retrofit.create(UserApi::class.java)

    @[Provides Singleton]
    fun providerCompanyApi(
        retrofit: Retrofit
    ):CompanyApi = retrofit.create(CompanyApi::class.java)

    @[Provides Singleton]
    fun providerProductApi(
        retrofit: Retrofit
    ):ProductApi = retrofit.create(ProductApi::class.java)

    @ExperimentalSerializationApi
    @[Provides Singleton]
    fun providerRetrofit(
        okHttpClient: OkHttpClient
    ):Retrofit = RetrofitInst.createRetrofit(okHttpClient)

    /**
     * Create OkHttpClient
     * @param sharedPreferences Authorization JWT Token
     * */
    @[Singleton Provides]
    fun providerOkHttpClient(
        sharedPreferences: SharedPreferences
    ):OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            // Get Authorization JWT Token
            val token = sharedPreferences.getString(
                com.example.core_database_data.repositoryImpl.UserRepositoryImpl.USER_TOKEN,
                ""
            )

            val request = it.request().newBuilder()
                //Add header Authorization JWT Token
                .addHeader("Authorization", "Bearer $token")
                .build()
            it.proceed(request = request)
        }
        .build()
}

@Module
interface ApiModuleBinds {

    @Binds
    fun providerUserCompanyRepository(
        userCompanyRepositoryImpl: UserCompanyRepositoryImpl
    ):UserCompanyRepository

    @Binds
    fun providerUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun providerCompanyRepository(
        companyRepositoryImpl: CompanyRepositoryImpl
    ):CompanyRepository

    @Binds
    fun providerProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ):ProductRepository
}