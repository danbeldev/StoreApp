package com.example.core_network_data.api

import com.example.core_model.data.api.company.PostCompany
import com.example.core_model.data.api.user.Authorization
import com.example.core_network_data.retrofit.RetrofitInst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class CompanyApiTest {

    // Create Retrofit
    private val retrofit = RetrofitInst.createRetrofit()

    // Create Api Company
    private val companyApi = retrofit.create(CompanyApi::class.java)

    // Create Api User
    private val userApi = retrofit.create(UserApi::class.java)

    @Test
    fun `get company`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            val pageSize = 20
            val pageNumber = 1

            //act
            // Request API get Company
            val response = companyApi.getCompany(
                pageNumber = pageSize,
                pageSize = pageNumber
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
        }
    }

    @Test
    fun `get company by id`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            // Test company id
            val companyId = 1

            // act
            // Request API get Company by id
            val response = companyApi.getCompanyById(companyId)

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
        }
    }

    @Test
    fun `create company`() = runTest {
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

        // Base crete company date
        val companyCreateInfo = PostCompany(
            title = "test_title",
            description = "test_description"
        )

        // act
        // Request API post authorization
        val authorizationResponse = userApi.authorization(authorization)

        // Authorization response body not null
        authorizationResponse.body()?.let { response ->
            // Save JWT Authorization Token
            token = response.access_token
        }

        // Request API post create company
        val createCompanyResponse = companyApi.postCompany(
            postCompany = companyCreateInfo,
            token = "Bearer $token"
        )

        // assert
        assertTrue(authorizationResponse.isSuccessful)
        assertNotNull(authorizationResponse.body())
        assertTrue(createCompanyResponse.isSuccessful)
    }
}