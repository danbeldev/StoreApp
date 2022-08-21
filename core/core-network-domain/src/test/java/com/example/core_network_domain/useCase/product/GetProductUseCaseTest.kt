package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.Product
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
class GetProductUseCaseTest {

    @Test
    fun `GetProductUseCase method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data product
            val product = Product(
                total = 31
            )

            // create product repository
            val repository = createRepository()

            // create GetProduct UseCase
            val useCase = createUseCase(repository)

            // create test impl method repository.getProduct(any())
            coEvery { repository.getProduct(any()) } returns product

            // act
            val response = useCase.invoke()

            // assert
            // method repository.getProduct(any()) must be done once
            coVerify {
                repository.getProduct(any())
            }
            confirmVerified(repository)
            // product and response data equal
            Assert.assertEquals(product, response)
        }
    }

    // create product repository
    private fun createRepository(): ProductRepository = mockk(relaxed = true)

    // create GetProduct UseCase
    private fun createUseCase(
        repository: ProductRepository = createRepository()
    ):GetProductUseCase = GetProductUseCase(
        productRepository = repository
    )
}