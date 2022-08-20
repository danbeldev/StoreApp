package com.example.core_network_data.repository

import com.example.core_model.data.api.product.Country
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.create.ProductCreate
import com.example.core_model.data.api.product.enums.AgeRating
import com.example.core_model.data.api.product.enums.ProductStatus
import com.example.core_model.data.api.product.enums.ProductType
import com.example.core_model.data.api.product.orderBy.ProductOrderBy
import com.example.core_model.data.api.product.review.ProductReview
import com.example.core_model.data.api.product.review.ProductReviewAdd
import com.example.core_model.data.enums.user.UserRole.BaseUser
import com.example.core_model.data.enums.user.UserRole.CompanyUser
import com.example.core_network_data.api.ProductApi
import com.example.core_network_data.common.BASE_URL
import com.example.core_network_domain.repository.ProductRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
): ProductRepository {

    /**
     * Authorization not required
     * @param pageNumber A page number within the paginated result set
     * @param search search product
     * @param genreId sorting product by genre [getGenre]
     * @param countryId sorting product by country [getCountry]
     * @param ageRating sorting product by age
     * @param free Is the app free
     * @param startDatePublication sorting product by start date publication
     * @param endDatePublication sorting product by end date publication
     * @param startRating sorting product by start rating
     * @param endRating sorting product by end ration
     * @param productType sorting product by type product
     * @param productStatus sorting product by status
     * @param orderBy sorting product by type
     * */
    override suspend fun getProduct(
        pageNumber:Int,
        search:String?,
        genreId:List<Int>?,
        countryId:List<Int>?,
        ageRating:AgeRating?,
        advertising:Boolean?,
        free:Boolean?,
        startDatePublication:String?,
        endDatePublication:String?,
        startRating:Float?,
        endRating:Float?,
        productType:ProductType?,
        productStatus:ProductStatus?,
        orderBy:ProductOrderBy?
    ): Product {
        return productApi.getProduct(
            pageNumber = pageNumber,
            search = search,
            genreId = genreId,
            countryId = countryId,
            ageRating = ageRating,
            advertising = advertising,
            free = free,
            startDatePublication = startDatePublication,
            endDatePublication = endDatePublication,
            startRating = startRating,
            endRating = endRating,
            productType = productType,
            productStatus = productStatus,
            orderBy = orderBy
        ).body() ?: Product()
    }

    /**
     *  Authorization not required
     *  @param id product id
     * */
    override suspend fun getProductById(id: Int): Response<ProductItem> {
        return productApi.getProductById(id)
    }

    /**
     * company add product
     * Authorization role [CompanyUser]
     * @param product product information
     * */
    override suspend fun postProduct(product: ProductCreate): Response<ProductItem> {
        return productApi.postProduct(product)
    }

    // Get product genres
    // Authorization not required
    override suspend fun getGenre(): Response<Genre> {
        return productApi.getGenre()
    }

    // Get product country
    // Authorization not required
    override suspend fun getCountry(): Response<Country> {
        return productApi.getCountry()
    }

    /**
     * Product add file
     * Authorization role [CompanyUser]
     * Get product file [BASE_URL]/api/Product/{id}/File
     * @param id product id
     * @param file product file
     * */
    override suspend fun postFile(id:Int,file: ByteArray): Response<Void?> {
        val requestFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file","product_file",requestFile)
        return productApi.postFile(file = body, id = id)
    }

    /**
     * Authorization not required
     * @param id product id
     * @param search search product reviews
     * */
    override suspend fun getProductReview(id: Int, search: String?): Response<ProductReview> {
        return productApi.getProductReview(id, search)
    }

    /**
     * get product file size
     * Authorization not required
     * @param id product id
     * */
    override suspend fun optionsProductFileSize(id: Int): String {
        return productApi.optionsProductFileSize(id).body() ?: ""
    }

    /**
     * Authorization role [BaseUser]
     * get product reviews [getProductReview]
     * @param id product id
     * @param review product info for add product review
     * */
    override suspend fun postProductReview(id:Int,review: ProductReviewAdd): Response<Unit?> {
        return productApi.postProductReview(id,review)
    }
}