package com.example.core_network_domain.useCase.userCompany

import com.example.core_model.data.api.company.CompanyItem
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
class GetUserCompanyUseCaseTest {

    @Test
    fun `GetUserCompanyUseCase method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create user company repository
            val repository = createRepository()

            // create get user company use case
            val useCase = createUseCase(repository)

            // create test data company info
            val company = CompanyItem(id = 10, title = "test_title")

            // create impl method repository.getCompany()
            coEvery { repository.getCompany() } returns Response.success(company)

            // act
            val response = useCase.invoke()

            // assert
            response.collect { result ->
                // method repository.getCompany() must be done once
                coVerify(exactly = 1) {
                    repository.getCompany()
                }
                confirmVerified(repository)
                // result message null
                Assert.assertNull(result.message)
                // result data and company must equal
                Assert.assertEquals(result.data,company)
            }
        }
    }

    // create user company repository
    private fun createRepository():UserCompanyRepository = mockk(relaxed = true)

    // create user company use case
    private fun createUseCase(
        repository: UserCompanyRepository = createRepository()
    ):GetUserCompanyUseCase = GetUserCompanyUseCase(
        userCompanyRepository = repository
    )
}