package com.example.core_network_domain.useCase.product

import com.example.core_model.data.api.product.review.ProductReview
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
class GetProductReviewUseCaseTest {

    @Test
    fun `GetProductReviewUseCase method invoke`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data product review
            val review = ProductReview(
                totalHasFourRating = 10,
                total = 20,
                totalHasOneRating = 10
            )

            // create product repository
            val repository = createRepository()

            // create GetProductReview UseCase
            val useCase = createUseCase(repository)

            // create test impl method repository.getProductReview(any(),any())
            coEvery { repository.getProductReview(any(),any()) } returns Response.success(review)

            // act
            val response = useCase.invoke(id = 1,search = null)

            // assert
            response.collect { result ->
                // method repository.getProductReview(any(),any()) must be done once
                coVerify {
                    repository.getProductReview(any(),any())
                }
                confirmVerified(repository)
                // result success
                Assert.assertTrue(result is Result.Success)
                // review and result data equal
                Assert.assertEquals(review, result.data)
            }
        }
    }

    // create product repository
    private fun createRepository(): ProductRepository = mockk(relaxed = true)

    // create GetProductReview UseCase
    private fun createUseCase(
        repository: ProductRepository = createRepository()
    ):GetProductReviewUseCase = GetProductReviewUseCase(
        productRepository = repository
    )
}