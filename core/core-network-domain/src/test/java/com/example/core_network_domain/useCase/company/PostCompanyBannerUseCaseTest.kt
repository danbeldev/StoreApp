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
class PostCompanyBannerUseCaseTest {

    @Test
    fun `PostCompanyBannerUseCase method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test company banner
            val banner = testByteArray

            // create company repository
            val repository = createRepository()

            // create postCompanyBanner useCase
            val useCase = createUseCase(repository)

            // create test impl method repository.postCompanyBanner(any())
            coEvery { repository.postCompanyBanner(any()) } returns Response.success(null)

            // act
            val response = useCase.invoke(banner = banner)

            // assert
            response.collect { result ->
                // method repository.getCompany(any()) must be done once
                coVerify {
                    repository.postCompanyBanner(any())
                }
                confirmVerified(repository)
                // result success
                Assert.assertTrue(result is Result.Success)
            }
        }
    }

    // create company repository
    private fun createRepository():CompanyRepository = mockk(relaxed = true)

    // create postCompanyBanner useCase
    private fun createUseCase(
        repository: CompanyRepository = createRepository()
    ):PostCompanyBannerUseCase = PostCompanyBannerUseCase(
        companyRepository = repository
    )
}