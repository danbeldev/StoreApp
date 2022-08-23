package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.Country
import com.example.core_network_domain.responseApi.Result
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
class GetCountryProductUseCaseTest {

    @Test
    fun `GetCountryProductUseCaseTest method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data country
            val country = Country(
                total = 31,
                items = emptyList()
            )

            // create product repository
            val repository = createRepository()

            // create getCountryProduct UseCase
            val useCase = createUseCase(repository)

            // create test impl method repository.getCountry()
            coEvery { repository.getCountry() } returns Response.success(country)

            // act
            val response = useCase.invoke()

            // assert
            response.collect { result ->
                // method repository.getCountry() must be done once
                coVerify {
                    repository.getCountry()
                }
                confirmVerified(repository)
                // result success
                Assert.assertTrue(result is Result.Success)
                // country and result data equal
                Assert.assertEquals(country, result.data)
            }
        }
    }

    // create product repository
    private fun createRepository():ProductRepository = mockk(relaxed = true)

    // create getCountryProduct UseCase
    private fun createUseCase(
        repository: ProductRepository = createRepository()
    ):GetCountryProductUseCase = GetCountryProductUseCase(
        productRepository = repository
    )
}