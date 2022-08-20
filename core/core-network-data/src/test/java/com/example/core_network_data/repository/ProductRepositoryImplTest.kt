package com.example.core_network_data.repository

import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.create.ProductCreate
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

    private fun createApi():ProductApi = mockk(relaxed = true)

    private fun createRepository(
        api:ProductApi = createApi()
    ):ProductRepository = ProductRepositoryImpl(
        productApi = api
    )
}