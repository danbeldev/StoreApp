package com.example.core_network_data.repository

import com.example.core_model.data.api.company.CompanyItem
import com.example.core_network_data.api.UserCompanyApi
import com.example.core_network_domain.repository.UserCompanyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserCompanyRepositoryImplTest {

    @Test
    fun `Get company`() = runTest {
        launch(Dispatchers.IO){
            /**
             * arrange
             * Create test company
             * Create api
             * Create repository
             * Impl method getCompany()
             * */
            val company = CompanyItem(
                banner = null,
                dateCreating = "13.13.2003",
                description = "description",
                icon = null,
                id = 1,
                logo = null,
                title = "title",
                totalProduct = 10
            )

            val api = createApi()

            val repository = createRepository(api)

            coEvery { api.getCompany() } returns Response.success(company)

            // act
            val response = repository.getCompany()

            /**
             * assert
             * method [api.getCompany()] must be done once
             * response code [200..300]
             * response body not null
             * test company and response body must equal
             * */
            coVerify(exactly = 1) {
                api.getCompany()
            }
            confirmVerified(api)
            Assert.assertTrue(response.isSuccessful)
            Assert.assertNotNull(response.body())
            Assert.assertEquals(response.body(), company)
        }
    }

    // create user company api
    private fun createApi():UserCompanyApi = mockk(relaxed  = true)

    // create user company repository
    private fun createRepository(
        api:UserCompanyApi = createApi()
    ):UserCompanyRepository = UserCompanyRepositoryImpl(
        userCompanyApi = api
    )
}