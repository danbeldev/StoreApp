package com.example.core_network_data.repository

import com.example.core_common.test.testByteArray
import com.example.core_model.data.api.product.*
import com.example.core_model.data.api.product.create.ProductCreate
import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_model.data.api.product.review.ProductReviewPush
import com.example.core_network_data.api.ProductApi
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
class ProductRepositoryImplTest {

    @Test
    fun `get products`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            // create test product
            val product = Product(
                total = 10,
                items = emptyList()
            )

            // create product api
            val api = createApi()

            // create product repository
            val repository = createRepository(api)

            // create test impl method api.getProduct(any())
            coEvery { api.getProduct(any()) } returns Response.success(product)

            // act
            val response = repository.getProduct()

            // assert
            // method [api.getProduct(any())] must be done once
            coVerify(exactly = 1) {
                api.getProduct(any())
            }
            confirmVerified(api)
            // test product and response body must equal
            Assert.assertEquals(product, response)
        }
    }

    @Test
    fun `get product by id`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            // create test date product
            val product = ProductItem(
                id = 10,
                title = "Test android app"
            )

            // create product api
            val api = createApi()

            // create product repository
            val repository = createRepository(api)

            // create test impl method api.getProductById(any())
            coEvery { api.getProductById(any()) } returns Response.success(product)

            // act
            val response = repository.getProductById(id = 1)

            // assert
            // method [api.getProductById(any())] must be done once
            coVerify(exactly = 1) {
                api.getProductById(any())
            }
            confirmVerified(api)
            // response code [200..300]
            Assert.assertTrue(response.isSuccessful)
            // response body not null
            Assert.assertNotNull(response.body())
            // test product and response body must equal
            Assert.assertEquals(response.body(), product)
        }
    }

    @Test
    fun `post product`() = runTest {
        launch(Dispatchers.IO){
            //arrange

            // create test date product
            val product = ProductItem(
                id = 10,
                title = "Test android app"
            )

            // test date create product
            val productCreate = ProductCreate(
                title = "Test android app"
            )

            // create product api
            val api = createApi()

            // create product repository
            val repository = createRepository(api)

            // create test impl method api.postProduct(any())
            coEvery { api.postProduct(any()) } returns Response.success(product)

            // act
            val response = repository.postProduct(productCreate)

            // assert
            // method [api.postProduct(any())] must be done once
            coVerify(exactly = 1) {
                api.postProduct(any())
            }
            confirmVerified(api)
            // response code [200..300]
            Assert.assertTrue(response.isSuccessful)
            // response body not null
            Assert.assertNotNull(response.body())
            // test product and response body must equal
            Assert.assertEquals(response.body(), product)
        }
    }

    @Test
    fun `get genre`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data genre
            val genre = Genre(
                total = 1,
                items = listOf(
                    GenreItem(
                        id = 1,
                        title = "Movie"
                    )
                )
            )

            // create product api
            val api = createApi()

            // create product repository
            val repository = createRepository(api)

            // create impl method api.getGenre()
            coEvery { api.getGenre() } returns Response.success(genre)

            // act
            val response = repository.getGenre()

            // assert
            // method api.getGenre() must be done once
            coVerify(exactly = 1) {
                api.getGenre()
            }
            confirmVerified(api)
            // response code [200..300]
            Assert.assertTrue(response.isSuccessful)
            // response body not null
            Assert.assertNotNull(response.body())
            // test data genre and response body must equal
            Assert.assertEquals(genre, response.body())
        }
    }

    @Test
    fun `get country`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data country
            val country = Country(
                total = 1,
                items = listOf(
                    CountryItem(
                        id = 1,
                        countryTitle = "country",
                        continent = "continent"
                    )
                )
            )

            // create product api
            val api = createApi()

            // create product repository
            val repository = createRepository(api)

            // create impl method api.getCountry()
            coEvery { api.getCountry() } returns Response.success(country)

            // act
            val response = repository.getCountry()

            // assert
            // method api.getCountry() must be done once
            coVerify(exactly = 1) {
                api.getCountry()
            }
            confirmVerified(api)
            // response code [200..300]
            Assert.assertTrue(response.isSuccessful)
            // response body not null
            Assert.assertNotNull(response.body())
            // test data country and response body must equal
            Assert.assertEquals(country, response.body())
        }
    }

    @Test
    fun `post file`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // test product id
            val productId = 1

            // test product file
            val file = testByteArray

            // create product api
            val api = createApi()

            // create product repository
            val repository = createRepository(api)

            // create impl method api.postFile(any(),any())
            coEvery { api.postFile(any(),any()) } returns Response.success(null)

            // act
            val response = repository.postFile(
                id = productId,
                file = file
            )

            // assert
            // method api.postFile(any(),any()) must be done once
            coVerify {
                api.postFile(any(),any())
            }
            confirmVerified(api)
            // response code [200..300]
            Assert.assertTrue(response.isSuccessful)
        }
    }

    @Test
    fun `get product review`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // test data product review
            val review = ProductReview(
                total = 10,
                totalHasOneRating = 5,
                totalHasThreeRating = 3,
                totalHasFourRating = 2
            )

            // create product api
            val api = createApi()

            // create product repository
            val repository = createRepository(api)

            // create impl method api.getProductReview(any(),any())
            coEvery { api.getProductReview(any(),any()) } returns Response.success(review)

            // act
            val response = repository.getProductReview(id = 1, search = null)

            // assert
            // method api.getProductReview(any(),any()) must be done once
            coVerify(exactly = 1) {
                api.getProductReview(any(),any())
            }
            confirmVerified(api)
            // response code [200..300]
            Assert.assertTrue(response.isSuccessful)
            // response body not null
            Assert.assertNotNull(response.body())
            // product review and response body must equal
            Assert.assertEquals(review, response.body())
        }
    }

    @Test
    fun `options product file size`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // test data  product file size
            val fileSize = "1 Gb"

            // create product api
            val api = createApi()

            // create product repository
            val repository = createRepository(api)

            // create impl method api.optionsProductFileSize(any())
            coEvery { api.optionsProductFileSize(any()) } returns Response.success(fileSize)

            // act
            val response = repository.optionsProductFileSize(id = 1)

            // assert
            // method api.optionsProductFileSize(any()) must be done once
            coVerify(exactly = 1) {
                api.optionsProductFileSize(any())
            }
            confirmVerified(api)
            // product file size and response must equal
            Assert.assertEquals(fileSize, response)
        }
    }

    @Test
    fun `post product review`() = runTest {
        launch(Dispatchers.IO){
            // arrange

            // create test data product review push
            val review = ProductReviewPush(
                title = "test_title",
                description = "test_description",
                rating = 1.2f,
                datePublication = "12.12.2002"
            )

            // create product api
            val api = createApi()

            // create product repository
            val repository = createRepository(api)

            // create impl method api.postProductReview(any(),any())
            coEvery { api.postProductReview(any(),any()) } returns Response.success(null)

            // act
            val response = repository.postProductReview(id = 1, review = review)

            // assert
            // method api.postProductReview(any(),any()) must be done once
            coVerify(exactly = 1) {
                api.postProductReview(any(),any())
            }
            confirmVerified(api)
            // response code [200..300]
            Assert.assertTrue(response.isSuccessful)
        }
    }

    private fun createApi():ProductApi = mockk(relaxed = true)

    private fun createRepository(
        api:ProductApi = createApi()
    ):ProductRepository = ProductRepositoryImpl(
        productApi = api
    )
}