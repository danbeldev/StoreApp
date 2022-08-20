package com.example.core_network_data.api

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
}