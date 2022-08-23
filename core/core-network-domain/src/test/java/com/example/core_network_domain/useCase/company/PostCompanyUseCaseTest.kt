package com.example.core_network_domain.useCase.company

import com.example.core_model.data.api.company.CreateCompany
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.repository.CompanyRepository
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
class PostCompanyUseCaseTest {

    @Test
    fun `PostCompanyUseCaseTest method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data create company
            val company = CreateCompany(
                title = "title_test",
                description = "description_test"
            )

            // create company repository
            val repository = createRepository()

            // create postCompany useCase
            val useCase = createUseCase(repository)

            // create test impl method repository.postCompany(any())
            coEvery { repository.postCompany(any()) } returns Response.success(Unit)

            // act
            val response = useCase.invoke(company)

            // assert
            response.collect { result ->
                // method repository.postCompany(any()) must be done once
                coVerify {
                    repository.postCompany(any())
                }
                confirmVerified(repository)
                // result success
                Assert.assertTrue(result is Result.Success)
            }
        }
    }

    // create company repository
    private fun createRepository(): CompanyRepository = mockk(relaxed = true)

    // create postCompanyLogo useCase
    private fun createUseCase(
        repository: CompanyRepository = createRepository()
    ):PostCompanyUseCase = PostCompanyUseCase(
        companyRepository = repository
    )
}