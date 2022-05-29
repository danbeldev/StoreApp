package com.example.core_network_domain.apiResponse

import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return Result.Success(body)
                }
            }
            return Result.Error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return Result.Error(e.message.toString())
        }
    }
}