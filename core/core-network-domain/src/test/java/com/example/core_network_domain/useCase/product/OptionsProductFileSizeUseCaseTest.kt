package com.example.core_network_domain.useCase.product

import com.example.core_network_domain.repository.ProductRepository
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
class OptionsProductFileSizeUseCaseTest {

    @Test
    fun `OptionsProductFileSizeUseCase method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data product file size
            val fileSize = "1 Gb"

            // create product repository
            val repository = createRepository()

            // create OptionsProductFileSizeUseCase UseCase
            val useCase = createUseCase(repository)

            // create test impl method repository.optionsProductFileSize(any())
            coEvery { repository.optionsProductFileSize(any()) } returns fileSize

            // act
            val response = useCase.invoke(id = 1)

            // assert
            // method repository.optionsProductFileSize(any()) must be done once
            coVerify {
                repository.optionsProductFileSize(any())
            }
            confirmVerified(repository)
            // fileSize and response equal
            Assert.assertEquals(fileSize, response)
        }
    }

    // create product repository
    private fun createRepository(): ProductRepository = mockk(relaxed = true)

    // create OptionsProductFileSizeUseCase UseCase
    private fun createUseCase(
        repository: ProductRepository = createRepository()
    ):OptionsProductFileSizeUseCase = OptionsProductFileSizeUseCase(
        productRepository = repository
    )
}