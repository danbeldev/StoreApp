package com.example.core_network_domain.useCase.company

import com.example.core_model.data.api.company.Company
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

@ExperimentalCoroutinesApi
class GetCompanyUseCaseTest {

    @Test
    fun `GetCompanyUseCaseTest method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data company
            val company = Company(
                total = 10
            )

            // create company repository
            val repository = createRepository()

            // create get company use case
            val useCase = createUseCase(repository)

            // create impl method repository.getCompany(any())
            coEvery { repository.getCompany(any()) } returns company

            // act
            val response = useCase.invoke(pageNumber = 1)

            // assert
            // method repository.getCompany(any()) must be done once
            coVerify(exactly = 1) {
                repository.getCompany(any())
            }
            confirmVerified(repository)
            // company and response equal
            Assert.assertEquals(company, response)
        }
    }

    // create user repository
    private fun createRepository(): CompanyRepository = mockk()

    // create get user useCase
    private fun createUseCase(
        repository: CompanyRepository = createRepository()
    ): GetCompanyUseCase = GetCompanyUseCase(
        companyRepository = repository
    )
}