package com.example.core_network_data.api

import com.example.core_model.data.api.product.Country
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.create.ProductCreate
import com.example.core_model.data.api.product.enums.ProductStatus
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
        @Query("pageNumber") pageNumber:Int
    ):Product

    @GET("$GET_PRODUCT/{id}")
    suspend fun getProductById(
        @Path("id") id:Int
    ):Response<ProductItem>

    @POST(POST_PRODUCT)
    suspend fun postProduct(
        @Body product:ProductCreate
    ):Response<Unit?>

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