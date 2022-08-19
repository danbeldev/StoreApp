package com.example.core_network_data.api

import com.example.core_model.data.api.product.enums.AgeRating
import com.example.core_model.data.api.product.enums.ProductStatus
import com.example.core_model.data.api.product.enums.ProductType
import com.example.core_network_data.retrofit.RetrofitInst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductApiTest {

    // Create Retrofit
    private val retrofit = RetrofitInst.createRetrofit()

    // Create Product Api
    private val productApi = retrofit.create(ProductApi::class.java)

    @Test
    fun `Get products`() = runTest {
        launch(Dispatchers.IO){

            // arrange
            val pageSize = 20
            val pageNumber = 1

            // act
            // Request API get Products
            val response = productApi.getProduct(
                pageSize = pageSize,
                pageNumber = pageNumber
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
        }
    }

    @Test
    fun `Get search products`() = runTest {
        launch(Dispatchers.IO){

            // arrange
            val pageSize = 20
            val pageNumber = 1
            val search = "Store App"

            // act
            // Request API get Products
            val response = productApi.getProduct(
                pageSize = pageSize,
                pageNumber = pageNumber,
                search = search
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())

            if (response.body()!!.items.isEmpty()) return@launch

            assertTrue(
                response.body()!!.items.any { product ->
                    product.title.contains(search) ||
                    product.shortDescription.contains(search) ||
                    product.fullDescription.contains(search)
                }
            )
        }
    }

    @Test
    fun `Get products sorting genre id`() = runTest {
        launch(Dispatchers.IO){

            // arrange
            val pageSize = 20
            val pageNumber = 1
            // Genre Movie
            val generesId = listOf(1)

            // act
            // Request API get Products
            val response = productApi.getProduct(
                pageSize = pageSize,
                pageNumber = pageNumber,
                genreId = generesId
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
            if (response.body()!!.items.isEmpty()) return@launch
            assertTrue(response.body()!!.items.any { product ->
                generesId.any { genreId ->
                    product.genre?.id == genreId
                }
            })
        }
    }

    @Test
    fun `Get products sorting country id`() = runTest {
        launch(Dispatchers.IO){

            // arrange
            val pageSize = 20
            val pageNumber = 1
            // Country Movie
            val countryId = listOf(1)

            // act
            // Request API get Products
            val response = productApi.getProduct(
                pageSize = pageSize,
                pageNumber = pageNumber,
                countryId = countryId
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
        }
    }

    @Test
    fun `Get products sorting age rating hss_g`() = runTest {
        launch(Dispatchers.IO){
            testProductAgeRating(
                productApi = productApi,
                ageRating = AgeRating.HAS_G
            )
        }
    }

    @Test
    fun `Get products sorting age rating hss_pg`() = runTest {
        launch(Dispatchers.IO){
            testProductAgeRating(
                productApi = productApi,
                ageRating = AgeRating.HAS_PG
            )
        }
    }

    @Test
    fun `Get products sorting age rating hss_pg13`() = runTest {
        launch(Dispatchers.IO){
            testProductAgeRating(
                productApi = productApi,
                ageRating = AgeRating.HAS_PG_13
            )
        }
    }

    @Test
    fun `Get products sorting age rating hss_r`() = runTest {
        launch(Dispatchers.IO){
            testProductAgeRating(
                productApi = productApi,
                ageRating = AgeRating.HAS_R
            )
        }
    }

    @Test
    fun `Get products sorting age rating hss_nc_17`() = runTest {
        launch(Dispatchers.IO){
            testProductAgeRating(
                productApi = productApi,
                ageRating = AgeRating.HAS_NC_17
            )
        }
    }

    @Test
    fun `Get products sorting advertising false`() = runTest {
        launch(Dispatchers.IO){
            testAdvertisingProduct(
                productApi = productApi,
                advertising = false
            )
        }
    }

    @Test
    fun `Get products sorting advertising true`() = runTest {
        launch(Dispatchers.IO){
            testAdvertisingProduct(
                productApi = productApi,
                advertising = true
            )
        }
    }

    @Test
    fun `Get products sorting free true`() = runTest {
        launch(Dispatchers.IO){
            testFreeProduct(
                productApi = productApi,
                free = true
            )
        }
    }

    @Test
    fun `Get products sorting free false`() = runTest {
        launch(Dispatchers.IO){
            testFreeProduct(
                productApi = productApi,
                free = false
            )
        }
    }

    @Test
    fun `Get products sorting start date publication`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            val pageSize = 20
            val pageNumber = 1

            val startDatePublication = "2022-05-27T13:57:55.487"

            // act
            // Request API get Products
            val response = productApi.getProduct(
                pageSize = pageSize,
                pageNumber = pageNumber,
                startDatePublication = startDatePublication
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())

            if (response.body()!!.items.isEmpty()) retrofit

            assertTrue(
                response.body()!!.items.any { product ->
                    product.datePublication >= startDatePublication
                }
            )
        }
    }

    @Test
    fun `Get products sorting end date publication`() = runTest {
        launch(Dispatchers.IO){
            // arrange
            val pageSize = 20
            val pageNumber = 1

            val endDatePublication = "2022-05-27T13:57:55.487"

            // act
            // Request API get Products
            val response = productApi.getProduct(
                pageSize = pageSize,
                pageNumber = pageNumber,
                endDatePublication = endDatePublication
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())

            if (response.body()!!.items.isEmpty()) retrofit

            assertTrue(
                response.body()!!.items.any { product ->
                    product.datePublication <= endDatePublication
                }
            )
        }
    }

    @Test
    fun `Get products sorting product type Android App`() = runTest {
        launch(Dispatchers.IO){
            testProductType(
                productApi = productApi,
                productType = ProductType.APP_ANDROID
            )
        }
    }

    @Test
    fun `Get products sorting product status examination`() = runTest {
        launch(Dispatchers.IO){
            testProductStatus(
                productApi = productApi,
                productStatus = ProductStatus.EXAMINATION
            )
        }
    }

    @Test
    fun `Get products sorting product status active`() = runTest {
        launch(Dispatchers.IO){
            testProductStatus(
                productApi = productApi,
                productStatus = ProductStatus.ACTIVE
            )
        }
    }

    @Test
    fun `Get products sorting product status blocked`() = runTest {
        launch(Dispatchers.IO){
            testProductStatus(
                productApi = productApi,
                productStatus = ProductStatus.BLOCKED
            )
        }
    }

    @Test
    fun `Get product by id`() = runTest {
        launch(Dispatchers.IO){

            // arrange
            // Test product id
            val productId = 1

            // act
            // Request API get Product by id
            val response = productApi.getProductById(
                id = productId
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
        }
    }

    @Test
    fun `Get genre`() = runTest {
        launch(Dispatchers.IO){

            // act
            // Request API get Product genres
            val response = productApi.getGenre()

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
        }
    }

    @Test
    fun `Get country`() = runTest {
        launch(Dispatchers.IO){

            // act
            // Request API get Product country
            val response = productApi.getCountry()

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
        }
    }

    @Test
    fun `Get product reviews`() = runTest {
        launch(Dispatchers.IO){

            // arrange
            val productId = 1

            // act
            // Request Api get reviews
            val response = productApi.getProductReview(
                id = productId,
                search = null
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
        }
    }

    @Test
    fun `Get product reviews sorting search`() = runTest {
        launch(Dispatchers.IO){

            // arrange
            val productId = 2
            val search = "a"

            // act
            // Request Api get reviews
            val response = productApi.getProductReview(
                id = productId,
                search = search
            )

            // assert
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())

            if (response.body()!!.items.isEmpty()) return@launch

            assertTrue(
                response.body()!!.items.any { product ->
                    product.title.contains(search) ||
                    product.description.contains(search)
                }
            )
        }
    }
}

private suspend fun testProductAgeRating(
    productApi:ProductApi,
    ageRating: AgeRating
){
    // arrange
    val pageSize = 20
    val pageNumber = 1

    // act
    // Request API get Products
    val response = productApi.getProduct(
        pageSize = pageSize,
        pageNumber = pageNumber,
        ageRating = ageRating
    )

    // assert
    assertTrue(response.isSuccessful)
    assertNotNull(response.body())

    if (response.body()!!.items.isEmpty()) return

    assertTrue(
        response.body()!!.items.any { product ->
            product.ageRating == ageRating
        }
    )
}

private suspend fun testAdvertisingProduct(
    productApi: ProductApi,
    advertising:Boolean
){
    // arrange
    val pageSize = 20
    val pageNumber = 1

    // act
    // Request API get Products
    val response = productApi.getProduct(
        pageSize = pageSize,
        pageNumber = pageNumber,
        advertising = advertising
    )

    // assert
    assertTrue(response.isSuccessful)
    assertNotNull(response.body())

    if (response.body()!!.items.isEmpty()) return

    assertTrue(
        response.body()!!.items.any { product ->
            product.advertising == advertising
        }
    )
}

private suspend fun testFreeProduct(
    productApi: ProductApi,
    free:Boolean
){
    // arrange
    val pageSize = 20
    val pageNumber = 1

    // act
    // Request API get Products
    val response = productApi.getProduct(
        pageSize = pageSize,
        pageNumber = pageNumber,
        free = free
    )

    // assert
    assertTrue(response.isSuccessful)
    assertNotNull(response.body())
}

private suspend fun testProductType(
    productApi: ProductApi,
    productType:ProductType
){
    // arrange
    val pageSize = 20
    val pageNumber = 1

    // act
    // Request API get Products
    val response = productApi.getProduct(
        pageSize = pageSize,
        pageNumber = pageNumber,
        productType = productType
    )

    // assert
    assertTrue(response.isSuccessful)
    assertNotNull(response.body())

    if (response.body()!!.items.isEmpty()) return

    assertTrue(
        response.body()!!.items.any { product ->
            product.productType == productType
        }
    )
}

private suspend fun testProductStatus(
    productApi: ProductApi,
    productStatus: ProductStatus
){
    // arrange
    val pageSize = 20
    val pageNumber = 1

    // act
    // Request API get Products
    val response = productApi.getProduct(
        pageSize = pageSize,
        pageNumber = pageNumber,
        productStatus = productStatus
    )

    // assert
    assertTrue(response.isSuccessful)
    assertNotNull(response.body())
}