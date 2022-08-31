package com.example.core_network_data.di

import com.example.core_common.annotations.di.UserToken
import com.example.core_network_data.retrofit.RetrofitInst
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @ExperimentalSerializationApi
    @[Provides Singleton]
    fun providerRetrofit(
        okHttpClient: OkHttpClient
    ):Retrofit = RetrofitInst.createRetrofit(okHttpClient)

    /**
     * Create OkHttpClient
     * @param token Authorization JWT Token
     * */
    @[Singleton Provides]
    fun providerOkHttpClient(
        @UserToken token:String
    ):OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request().newBuilder()
                //Add header Authorization JWT Token
                .addHeader("Authorization", "Bearer $token")
                .build()
            it.proceed(request = request)
        }
        .build()
}