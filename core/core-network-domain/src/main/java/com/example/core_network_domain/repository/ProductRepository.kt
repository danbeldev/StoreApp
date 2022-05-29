package com.example.core_network_domain.repository

import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.ProductItem
import retrofit2.Response

interface ProductRepository {

    suspend fun getProduct(
        pageNumber:Int
    ): Product

    suspend fun getProductById(
        id: Int
    ):Response<ProductItem>
}