package com.example.core_network_data.retrofit

import com.example.core_network_data.common.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RetrofitInst {

    companion object {

        //Create kotlinx serialization json
        private val json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

        // Create OkHttp MediaType
        private val contentType = "application/json".toMediaTypeOrNull()!!

        fun createRetrofit(
            okHttpClient:OkHttpClient? = null
        ):Retrofit {
            val retrofitBuilder = Retrofit.Builder()
                // Add base url
                .baseUrl(BASE_URL)
                // Add json converter kotlinx serialization
                .addConverterFactory(json.asConverterFactory(contentType))

            // Add OkHttp Client
            okHttpClient?.let {
                retrofitBuilder.client(okHttpClient)
            }

            return retrofitBuilder.build()
        }
    }
}