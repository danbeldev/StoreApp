package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.Genre
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
class GetGenreProductUseCaseTest {

    @Test
    fun `GetGenreProductUseCase method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data genre
            val genre = Genre(
                total = 31,
                items = emptyList()
            )

            // create product repository
            val repository = createRepository()

            // create getCountryProduct UseCase
            val useCase = createUseCase(repository)

            // create test impl method repository.getGenre()
            coEvery { repository.getGenre() } returns Response.success(genre)

            // act
            val response = useCase.invoke()

            // assert
            response.collect { result ->
                // method repository.getGenre() must be done once
                coVerify {
                    repository.getGenre()
                }
                confirmVerified(repository)
                // result success
                Assert.assertTrue(result is Result.Success)
                // genre and result data equal
                Assert.assertEquals(genre, result.data)
            }
        }
    }

    // create product repository
    private fun createRepository(): ProductRepository = mockk(relaxed = true)

    // create getGenreProduct UseCase
    private fun createUseCase(
        repository: ProductRepository = createRepository()
    ):GetGenreProductUseCase = GetGenreProductUseCase(
        productRepository = repository
    )
}