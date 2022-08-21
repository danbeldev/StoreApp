package com.example.core_network_domain.repository

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
import retrofit2.Response

interface ProductRepository {

    /**
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
    suspend fun getProduct(
        pageNumber:Int = 1,
        search:String? = null,
        genreId:List<Int>? = null,
        countryId:List<Int>? = null,
        ageRating:AgeRating? = null,
        advertising:Boolean? = null,
        free:Boolean? = null,
        startDatePublication:String? = null,
        endDatePublication:String? = null,
        startRating:Float? = null,
        endRating:Float? = null,
        productType:ProductType? = null,
        productStatus:ProductStatus? = null,
        orderBy:ProductOrderBy? = null
    ): Product

    /**
     *  @param id product id
     * */
    suspend fun getProductById(
        id: Int
    ):Response<ProductItem>

    /**
     * @param product product information
     * */
    suspend fun postProduct(
        product:ProductCreate
    ):Response<ProductItem>

    // Get product genres
    suspend fun getGenre():Response<Genre>

    // Get product country
    suspend fun getCountry():Response<Country>

    /**
     * Product add file
     * @param id product id
     * @param file product file
     * */
    suspend fun postFile(id:Int,file:ByteArray):Response<Void?>

    /**
     * @param id product id
     * @param search search product reviews
     * */
    suspend fun getProductReview(id: Int, search: String?):Response<ProductReview>

    /**
     * get product file size
     * @param id product id
     * */
    suspend fun optionsProductFileSize(id: Int):String

    /**
     * get product reviews [getProductReview]
     * @param id product id
     * @param review product info for add product review
     * */
    suspend fun postProductReview(id:Int, review:ProductReviewPush):Response<Unit?>
}