package com.example.core_network_data.api

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
import com.example.core_model.data.api.product.review.ProductReviewPush
import com.example.core_model.data.enums.user.UserRole.BaseUser
import com.example.core_model.data.enums.user.UserRole.CompanyUser
import com.example.core_network_data.common.BASE_URL
import com.example.core_network_data.common.ConstantsUrl.GET_PRODUCT
import com.example.core_network_data.common.ConstantsUrl.GET_PRODUCT_COUNTRY
import com.example.core_network_data.common.ConstantsUrl.GET_PRODUCT_GENRE
import com.example.core_network_data.common.ConstantsUrl.OPTIONS_PRODUCT_FILE
import com.example.core_network_data.common.ConstantsUrl.POST_PRODUCT
import com.example.core_network_data.common.ConstantsUrl.POST_PRODUCT_FILE
import com.example.core_network_data.common.ConstantsUrl.PRODUCT_REVIEW_URL
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {

    /**
     * Get products
     * Authorization not required
     * @param pageSize A page number within the paginated result set
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
    @GET(GET_PRODUCT)
    suspend fun getProduct(
        @Query("pageSize") pageSize:Int = 20,
        @Query("pageNumber") pageNumber:Int = 1,
        @Query("search") search:String? = null,
        @Query("genreId") genreId:List<Int>? = null,
        @Query("countryId") countryId:List<Int>? = null,
        @Query("ageRating") ageRating:AgeRating? = null,
        @Query("advertising") advertising:Boolean? = null,
        @Query("free") free:Boolean? = null,
        @Query("startDatePublication") startDatePublication:String? = null,
        @Query("endDatePublication") endDatePublication:String? = null,
        @Query("startRating") startRating:Float? = null,
        @Query("endRating") endRating:Float? = null,
        @Query("productType") productType:ProductType? = null,
        @Query("productStatus") productStatus:ProductStatus? = null,
        @Query("orderBy") orderBy: ProductOrderBy? = null
    ):Response<Product>

    /**
     *  Authorization not required
     *  @param id product id
    * */
    @GET("$GET_PRODUCT/{id}")
    suspend fun getProductById(
        @Path("id") id:Int
    ):Response<ProductItem>

    /**
     * company add product
     * Authorization role [CompanyUser]
     * @param product product information
     * */
    @POST(POST_PRODUCT)
    suspend fun postProduct(
        @Body product:ProductCreate
    ):Response<ProductItem>

    // Get product genres
    // Authorization not required
    @GET(GET_PRODUCT_GENRE)
    suspend fun getGenre():Response<Genre>

    // Get product country
    // Authorization not required
    @GET(GET_PRODUCT_COUNTRY)
    suspend fun getCountry():Response<Country>

    /**
     * Product add file
     * Authorization role [CompanyUser]
     * Get product file [BASE_URL]/api/Product/{id}/File
     * @param id product id
     * @param file product file
     * */
    @Multipart
    @POST(POST_PRODUCT_FILE)
    suspend fun postFile(
        @Path("id") id:Int,
        @Part file: MultipartBody.Part
    ):Response<Void?>

    /**
     * Authorization not required
     * @param id product id
     * @param search search product reviews
     * */
    @GET(PRODUCT_REVIEW_URL)
    suspend fun getProductReview(
        @Path("id") id: Int,
        @Query("search") search:String?
    ):Response<ProductReview>

    /**
     * get product file size
     * Authorization not required
     * @param id product id
     * */
    @OPTIONS(OPTIONS_PRODUCT_FILE)
    suspend fun optionsProductFileSize(
        @Path("id") id:Int
    ):Response<String>

    /**
     * Authorization role [BaseUser]
     * get product reviews [getProductReview]
     * @param id product id
     * @param productReviewAdd product info for add product review
     * */
    @POST(PRODUCT_REVIEW_URL)
    suspend fun postProductReview(
        @Path("id") id:Int,
        @Body productReviewAdd: ProductReviewPush
    ):Response<Unit?>
}