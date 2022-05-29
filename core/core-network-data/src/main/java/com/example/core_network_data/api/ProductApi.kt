package com.example.core_network_data.api

import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.enums.ProductStatus
import com.example.core_network_data.common.ConstantsUrl.GET_PRODUCT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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
}