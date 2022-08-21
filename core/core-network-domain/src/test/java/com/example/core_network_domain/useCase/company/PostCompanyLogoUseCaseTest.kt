package com.example.core_network_domain.useCase.company

import com.example.core_common.test.testByteArray
import com.example.core_network_domain.apiResponse.Result
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
class PostCompanyLogoUseCaseTest {

    @Test
    fun `PostCompanyLogoUseCaseTest method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            // create test company logo
            val logo = testByteArray

            // create company repository
            val repository = createRepository()

            // create postCompanyLogo useCase
            val useCase = createUseCase(repository)

            // create test impl method repository.postCompanyLogo(any())
            coEvery { repository.postCompanyLogo(any()) } returns Response.success(null)

            // act
            val response = useCase.invoke(logo)

            // assert
            response.collect { result ->
                // method repository.postCompanyLogo(any()) must be done once
                coVerify {
                    repository.postCompanyLogo(any())
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
    ):PostCompanyLogoUseCase = PostCompanyLogoUseCase(
        companyRepository = repository
    )
}