package com.example.core_network_data.repository

import com.example.core_common.test.testByteArray
import com.example.core_model.data.api.company.Company
import com.example.core_model.data.api.company.CreateCompany
import com.example.core_network_data.api.CompanyApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class CompanyRepositoryImplTest {

    // Create MockK
    @get:Rule
    val rule = MockKRule(this)

    // Create MockK CompanyApi
    @RelaxedMockK
    lateinit var companyApi:CompanyApi

    // Company Repository Inject MockK
    @InjectMockKs
    lateinit var repository:CompanyRepositoryImpl

    @Test
    fun `get company`() = runTest {
        launch(Dispatchers.IO){
            //arrange
            // Create test company
            val company = Company(
                total = 10,
                items = emptyList()
            )

            // Impl method getCompany()
            coEvery { companyApi.getCompany() } returns Response.success(company)

            // act
            val response = repository.getCompany()

            /**
             * assert
             * method [companyApi.getCompany()] must be done once
             * test company and response must equal
             */
            coVerify(exactly = 1) {
                companyApi.getCompany()
            }
            confirmVerified(companyApi)
            Assert.assertEquals(company, response)
        }
    }

    @Test
    fun `post company`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            // Create test data company info
            val companyInfoCreate = CreateCompany(
                title = "title",
                description = "description"
            )

            // Impl method postCompany(any())
            coEvery { companyApi.postCompany(any()) } returns Response.success(Unit)

            // act
            val response = repository.postCompany(companyInfoCreate)

            /**
             * assert
             * method [repository.getCompany()] must be done once
             * response code [200..300]
             * response body not null
             * */
            coVerify(exactly = 1) {
                companyApi.postCompany(any())
            }
            confirmVerified(companyApi)

            Assert.assertTrue(response.isSuccessful)
            Assert.assertNotNull(response.body())
        }
    }

    @Test
    fun `post company logo`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            // test company logo byte array
            val logo = testByteArray

            // create impl method companyApi.postCompanyLogo(any())
            coEvery { companyApi.postCompanyLogo(any()) } returns Response.success(Unit)

            // act
            val response = repository.postCompanyLogo(logo)

            // assert
            // method [api.postCompanyLogo(any())] must be done once
            coVerify(exactly = 1) {
                companyApi.postCompanyLogo(any())
            }
            confirmVerified(companyApi)
            // response code [200..300]
            Assert.assertTrue(response.isSuccessful)
            // response body not null
            Assert.assertNotNull(response.body())
            // Unit and response body must equal
            Assert.assertEquals(Unit, response.body())
        }
    }

    @Test
    fun `post company banner`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            // test company banner byte array
            val banner = testByteArray

            // create impl method companyApi.postCompanyBanner(any())
            coEvery { companyApi.postCompanyBanner(any()) } returns Response.success(null)

            // act
            val response = repository.postCompanyBanner(banner)

            // assert
            // method [api.postCompanyBanner(any())] must be done once
            coVerify(exactly = 1) {
                companyApi.postCompanyBanner(any())
            }
            confirmVerified(companyApi)
            // response code [200..300]
            Assert.assertTrue(response.isSuccessful)
        }
    }
}