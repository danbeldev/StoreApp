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
import com.example.core_network_data.common.ConstantsUrl.GET_PRODUCT
import com.example.core_network_data.common.ConstantsUrl.GET_PRODUCT_COUNTRY
import com.example.core_network_data.common.ConstantsUrl.GET_PRODUCT_GENRE
import com.example.core_network_data.common.ConstantsUrl.POST_PRODUCT
import com.example.core_network_data.common.ConstantsUrl.POST_PRODUCT_FILE
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {

    @GET(GET_PRODUCT)
    suspend fun getProduct(
        @Query("pageSize") pageSize:Int = 20,
        @Query("pageNumber") pageNumber:Int,
        @Query("search") search:String?,
        @Query("genreId") genreId:List<Int>?,
        @Query("countryId") countryId:List<Int>?,
        @Query("ageRating") ageRating:AgeRating?,
        @Query("advertising") advertising:Boolean?,
        @Query("free") free:Boolean?,
        @Query("startDatePublication") startDatePublication:String?,
        @Query("endDatePublication") endDatePublication:String?,
        @Query("startRating") startRating:Float?,
        @Query("endRating") endRating:Float?,
        @Query("productType") productType:ProductType?,
        @Query("productStatus") productStatus:ProductStatus?,
        @Query("orderBy") orderBy: ProductOrderBy?
    ):Product

    @GET("$GET_PRODUCT/{id}")
    suspend fun getProductById(
        @Path("id") id:Int
    ):Response<ProductItem>

    @POST(POST_PRODUCT)
    suspend fun postProduct(
        @Body product:ProductCreate
    ):Response<ProductItem>

    @GET(GET_PRODUCT_GENRE)
    suspend fun getGenre():Response<Genre>

    @GET(GET_PRODUCT_COUNTRY)
    suspend fun getCountry():Response<Country>

    @Multipart
    @POST(POST_PRODUCT_FILE)
    suspend fun postFile(
        @Path("id") id:Int,
        @Part file: MultipartBody.Part
    ):Response<Void?>
}