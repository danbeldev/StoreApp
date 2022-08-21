package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.apiResponse.Result
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
import retrofit2.Response

@ExperimentalCoroutinesApi
class GetProductByIdUseCaseTest {

    @Test
    fun `GetProductByIdUseCaseTest method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data product
            val product = ProductItem(
                title = "test_product"
            )

            // create product repository
            val repository = createRepository()

            // create getCountryProduct UseCase
            val useCase = createUseCase(repository)

            // create test impl method repository.getProductById(any())
            coEvery { repository.getProductById(any()) } returns Response.success(product)

            // act
            val response = useCase.invoke(id = 1)

            // assert
            response.collect { result ->
                // method repository.getProductById(any()) must be done once
                coVerify {
                    repository.getProductById(any())
                }
                confirmVerified(repository)
                // result success
                Assert.assertTrue(result is Result.Success)
                // product and result data equal
                Assert.assertEquals(product, result.data)
            }
        }
    }

    // create product repository
    private fun createRepository(): ProductRepository = mockk(relaxed = true)

    // create GetProductById UseCase
    private fun createUseCase(
        repository: ProductRepository = createRepository()
    ):GetProductByIdUseCase = GetProductByIdUseCase(
        productRepository = repository
    )
}