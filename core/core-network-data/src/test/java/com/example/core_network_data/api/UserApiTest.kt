package com.example.core_network_data.api

import com.example.core_model.data.api.user.Authorization
import com.example.core_network_data.retrofit.RetrofitInst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class UserApiTest {

    // Create Retrofit
    private val retrofit = RetrofitInst.createRetrofit()

    // Create User Api
    private val userApi = retrofit.create(UserApi::class.java)

    @Test
    fun `Authorization and get user info`() = runTest {
        launch(Dispatchers.IO){

            // arrange
            // Test user email
            val email = "company@."
            // Test user password
            val password = "companyPas"
            // JWT Authorization Token
            var token = ""

            // Authorization date
            val authorization = Authorization(
                email = email,
                password = password
            )

            // act
            val authorizationResponse = userApi.authorization(
                authorization = authorization
            )

            authorizationResponse.body()?.let { body ->
                token = body.access_token
            }

            val userInfoResponse = userApi.getUser(
                token = "Bearer $token"
            )

            // assert

            // Authorization
            assertTrue(authorizationResponse.isSuccessful)
            assertNotNull(authorizationResponse.body())

            // User info
            assertTrue(userInfoResponse.isSuccessful)
            assertNotNull(userInfoResponse.body())
            assertEquals(userInfoResponse.body()!!.email, email)
        }
    }
}