package com.example.core_network_domain.repository

import com.example.core_model.data.api.product.Country
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.Product
import com.example.core_model.data.api.product.ProductItem
import com.example.core_model.data.api.product.create.ProductCreate
import retrofit2.Response

interface ProductRepository {

    suspend fun getProduct(
        pageNumber:Int
    ): Product

    suspend fun getProductById(
        id: Int
    ):Response<ProductItem>

    suspend fun postProduct(
        product:ProductCreate
    ):Response<Unit?>

    suspend fun getGenre():Response<Genre>

    suspend fun getCountry():Response<Country>
}